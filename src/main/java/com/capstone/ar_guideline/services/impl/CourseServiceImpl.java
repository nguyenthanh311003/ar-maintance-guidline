package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CourseMapper;
import com.capstone.ar_guideline.mappers.LessonMapper;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.services.ICompanyService;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.ILessonService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseServiceImpl implements ICourseService {

  CourseRepository courseRepository;
  RedisTemplate<String, Object> redisTemplate;
  ICompanyService companyService;

  @Autowired @Lazy MiddleEnrollmentServiceImpl middleService;
  @Autowired @Lazy ILessonService lessonService;

  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_COURSE,
  };

  @Override
  public PagingModel<CourseResponse> findAll(
      int page,
      int size,
      boolean isEnrolled,
      Boolean isMandatory,
      String userId,
      String searchTemp,
      String companyId,
      String status) {
    try {
      PagingModel<CourseResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      List<Course> courses = new ArrayList<>();
      // create key for redis
      String hashKeyForCourse =
          page + ":" + size + ":" + isEnrolled + ":" + userId + ":" + searchTemp + ":" + status;
      List<CourseResponse> courseResponses = new ArrayList<>();

      // check if key exist in redis
      if (redisTemplate.opsForHash().hasKey(ConstHashKey.HASH_KEY_COURSE, hashKeyForCourse)) {
        // get data from redis
        courses =
            (List<Course>)
                redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_COURSE, hashKeyForCourse);
        courseResponses =
            courses.stream()
                .map(CourseMapper::fromEntityToCourseResponse)
                .collect(Collectors.toList());
      } else {
        // get data from database
        if (isEnrolled) {
          courses =
              courseRepository.findAllCourseEnrolledBy(
                  pageable, isMandatory, userId, searchTemp, status);
        } else {
          courses = courseRepository.findAllBy(pageable, searchTemp, status,companyId);
        }
        courseResponses =
            courses.stream()
                .map(
                    course -> {
                      CourseResponse response = CourseMapper.fromEntityToCourseResponse(course);
                      response.setNumberOfParticipants(
                          middleService.countByCourseId(course.getId()));
                      response.setNumberOfLessons(lessonService.countByCourseId(course.getId()));
                      response.setDuration(
                          middleService.getDurationOfCourseByCourseId(course.getId()));
                      return response;
                    })
                .collect(Collectors.toList());
        redisTemplate
            .opsForHash()
            .put(ConstHashKey.HASH_KEY_COURSE + ":all", hashKeyForCourse, courseResponses);
      }

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems(courseResponses.size());
      pagingModel.setTotalPages(UtilService.getTotalPage(courseResponses.size(), size));
      pagingModel.setObjectList(courseResponses);
      return pagingModel;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public CourseResponse create(CourseCreationRequest request) {
    try {
      Course newCourse = CourseMapper.fromCourseCreationRequestToEntity(request);
      newCourse.setStatus(ConstStatus.INACTIVE_STATUS);
      newCourse.setDuration(0);
      Company company = new Company();
      company.setId(request.getCompanyId());

      newCourse.setCompany(company);
      newCourse = courseRepository.save(newCourse);
      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return CourseMapper.fromEntityToCourseResponse(newCourse);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_CREATE_FAILED);
    }
  }

  @Override
  public CourseResponse update(String id, CourseCreationRequest request) {
    try {
      Set<String> keysToDelete = redisTemplate.keys(ConstHashKey.HASH_KEY_COURSE);
      Course courseById = findById(id);
      courseById = CourseMapper.fromCourseCreationRequestToEntity(request);
      courseRepository.save(courseById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return CourseMapper.fromEntityToCourseResponse(courseById);

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Course courseById = findById(id);
      courseRepository.deleteById(courseById.getId());
      // delete all cache of course
      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_DELETE_FAILED);
    }
  }

  @Override
  public Course findById(String id) {
    try {

      return courseRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public CourseResponse findByIdResponse(String id) {
    try {
      Course courseById = findById(id);
      CourseResponse courseResponse = CourseMapper.fromEntityToCourseResponse(courseById);
      courseResponse.setNumberOfParticipants(middleService.countByCourseId(courseById.getId()));
      courseResponse.setNumberOfLessons(lessonService.countByCourseId(courseById.getId()));
      courseResponse.setLessons(lessonService.findByCourseId(courseById.getId()));
      return courseResponse;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public CourseResponse findByTitleResponse(String title) {
    try {
      Course courseByTitle =
          courseRepository
              .findByTitle(title)
              .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
      return CourseMapper.fromEntityToCourseResponse(courseByTitle);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public List<CourseResponse> findByCompanyId(String companyId) {
    try {
      companyService.findByIdReturnEntity(companyId);

      List<Course> coursesByCompanyId = courseRepository.findByCompanyId(companyId);

      return coursesByCompanyId.stream()
          .map(
              course -> {
                CourseResponse courseResponse = CourseMapper.fromEntityToCourseResponse(course);
                courseResponse.setLessons(
                    course.getLessons().stream()
                        .map(LessonMapper::FromEntityToLessonResponse)
                        .toList());
                return courseResponse;
              })
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public List<CourseResponse> findCourseNoMandatory() {
    try {
      List<Course> coursesNoMandatory = courseRepository.findCourseNoMandatory();

      return coursesNoMandatory.stream()
          .map(
              c -> {
                CourseResponse courseResponse = CourseMapper.fromEntityToCourseResponse(c);
                courseResponse.setLessons(
                    c.getLessons().stream().map(LessonMapper::FromEntityToLessonResponse).toList());
                return courseResponse;
              })
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.FIND_COURSE_NO_MANDATORY_FAILED);
    }
  }

  @Override
  public Course save(Course course) {
    try {
      return courseRepository.save(course);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
    }
  }
}
