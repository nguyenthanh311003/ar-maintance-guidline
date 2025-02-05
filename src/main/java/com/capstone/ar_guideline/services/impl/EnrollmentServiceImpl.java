package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Enrollment;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CourseMapper;
import com.capstone.ar_guideline.mappers.EnrollmentMapper;
import com.capstone.ar_guideline.repositories.EnrollmentRepository;
import com.capstone.ar_guideline.services.IEnrollmentService;
import com.capstone.ar_guideline.services.ILessonProcessService;
import com.capstone.ar_guideline.services.IUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EnrollmentServiceImpl implements IEnrollmentService {
  EnrollmentRepository enrollmentRepository;

  @Lazy MiddleCourseServiceImpl middleService;

  IUserService userService;

  ILessonProcessService lessonProcessService;

  @Override
  public EnrollmentResponse create(EnrollmentCreationRequest request) {
    try {
      Course course = middleService.findCourseById(request.getCourseId());

      User user = userService.findById(request.getUserId());

      Enrollment newEnrollment =
          EnrollmentMapper.fromEnrolmentCreationRequestToEntity(request, course, user);
      newEnrollment.setIsCompleted(false);
      newEnrollment.setCompletionDate(null);
      newEnrollment.setEnrollmentDate(null);
      newEnrollment = enrollmentRepository.save(newEnrollment);

      return EnrollmentMapper.FromEntityToEnrollmentResponse(newEnrollment);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_CREATE_FAILED);
    }
  }

  @Override
  public EnrollmentResponse update(String id, EnrollmentCreationRequest request) {
    try {
      Enrollment enrollmentById = findById(id);

      Course courseById = middleService.findCourseById(request.getCourseId());

      User userById = userService.findById(request.getUserId());

      enrollmentById.setCourse(courseById);
      enrollmentById.setUser(userById);

      enrollmentById = enrollmentRepository.save(enrollmentById);

      return EnrollmentMapper.FromEntityToEnrollmentResponse(enrollmentById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_UPDATE_FAILED);
    }
  }

  @Override
  public EnrollmentResponse changeStatusToTrue(String id) {
    try {
      Enrollment enrollmentById = findById(id);

      enrollmentById.setIsCompleted(true);
      LocalDateTime currentDateTime = LocalDateTime.now();
      enrollmentById.setCompletionDate(currentDateTime);

      enrollmentById = enrollmentRepository.save(enrollmentById);

      return EnrollmentMapper.FromEntityToEnrollmentResponse(enrollmentById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Enrollment enrollmentById = findById(id);

      enrollmentRepository.deleteById(enrollmentById.getId());

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_DELETE_FAILED);
    }
  }

  @Override
  public Enrollment findById(String id) {
    try {
      return enrollmentRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_NOT_EXISTED);
    }
  }

  @Override
  public List<EnrollmentResponse> createAll(List<EnrollmentCreationRequest> requests) {

    try {
      List<EnrollmentResponse> responses = new ArrayList<>();
      for (EnrollmentCreationRequest request : requests) {
        responses.add(create(request));
        lessonProcessService.createAll(request.getCourseId(), request.getUserId());
      }
      return responses;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_CREATE_FAILED);
    }
  }

  @Override
  public List<EnrollmentResponse> findCourseIsRequiredForUser(
      String userId, Boolean isRequiredCourse) {
    try {
      userService.findById(userId);
      List<Enrollment> enrollments =
          enrollmentRepository.findByUserIdAndEnrollmentDate(userId, isRequiredCourse);

      return enrollments.stream()
          .map(
              e -> {
                EnrollmentResponse enrollmentResponse =
                    EnrollmentMapper.FromEntityToEnrollmentResponse(e);
                enrollmentResponse.setCourseResponse(
                    CourseMapper.fromEntityToCourseResponse(e.getCourse()));
                return enrollmentResponse;
              })
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.FIND_COURSE_MANDATORY_FAILED);
    }
  }

  @Override
  public boolean checkUserIsAssign(String userId, String courseId) {
    try {
      Optional<Enrollment> enrollmentByUserId = enrollmentRepository.findByUserId(userId, courseId);

      return enrollmentByUserId.isPresent();

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_NOT_EXISTED);
    }
  }

  @Override
  public List<Enrollment> findByCourseId(String courseId) {
    try {
      return enrollmentRepository.findByCourseId(courseId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_NOT_EXISTED);
    }
  }

  @Override
  public Integer countByCourseIdAndEnrollmentDateNotNull(String courseId) {
    try {
      return enrollmentRepository.countByCourseIdAndEnrollmentDateIsNotNull(courseId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_NOT_EXISTED);
    }
  }
}
