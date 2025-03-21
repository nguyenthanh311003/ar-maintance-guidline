package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CourseMapper;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.repositories.InstructionDetailRepository;
import com.capstone.ar_guideline.repositories.ServicePricerRepository;
import com.capstone.ar_guideline.services.*;
import com.capstone.ar_guideline.util.UtilService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseServiceImpl implements ICourseService {

  CourseRepository courseRepository;
  RedisTemplate<String, Object> redisTemplate;
  ICompanyService companyService;
  IModelService modelService;
  IInstructionService instructionService;
  IMachineTypeService machineTypeService;
  InstructionDetailRepository instructionDetailRepository;
  ServicePricerRepository servicePricerRepository;
  WalletServiceImpl walletService;

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
        courseResponses =
            courses.stream()
                .map(
                    course -> {
                      CourseResponse response = CourseMapper.fromEntityToCourseResponse(course);
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
      Model model = modelService.findById(request.getModelId());
      companyService.findById(request.getCompanyId());
      newCourse.setModelType(model.getModelType());
      newCourse.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      newCourse.setStatus("DRAFTED");
      newCourse.setCourseCode(UUID.randomUUID().toString());
      newCourse.setDuration(0);
      newCourse.setNumberOfScan(0);
      newCourse.setQrCode(UtilService.generateAndStoreQRCode(newCourse.getCourseCode()));
      newCourse = courseRepository.save(newCourse);

      //      Arrays.stream(keysToRemove)
      //          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
      //          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

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
      Course courseById = findById(id);
      courseById = CourseMapper.fromCourseCreationRequestToEntity(request);
      if (request.getImageUrl() != null) {
        courseById.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      }
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
  public String updateCoursePicture(String courseId, MultipartFile file) {
    try {
      Course course = findById(courseId);
      course.setImageUrl(FileStorageService.storeFile(file));
      courseRepository.save(course);
      return course.getImageUrl();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
    }
  }

  @Override
  public Course findByModelId(String modelId) {
    try {
      return courseRepository.findByModelId(modelId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public void changeStatusByCourseId(String courseId) {
    try {
      Course courseById = findById(courseId);

      if (courseById.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
        courseById.setStatus(ConstStatus.ACTIVE_STATUS);
      } else {
        courseById.setStatus(ConstStatus.INACTIVE_STATUS);
      }

      courseRepository.save(courseById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
    }
  }

  @Override
  public void updateNumberOfScan(String id) {
    Course course = findById(id);
    course.setNumberOfScan(course.getNumberOfScan() + 1);
    courseRepository.save(course);
  }

  @Override
  @Transactional
  public void publishGuidelineFirstTime(String courseId, String userId) {
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

    // Count the number of InstructionDetail
    int instructionDetailCount =
        instructionDetailRepository.countInstructionDetailByCourseId(courseId);

    if (instructionDetailCount < 0) {
      throw new RuntimeException("Must have at lease 1 instruction detail");
    }

    // Find the ServicePrice with the name "Instruction Detail"
    ServicePrice servicePrice = servicePricerRepository.findByName("Instruction Detail");
    if (servicePrice == null) {
      throw new RuntimeException("ServicePrice 'Instruction Detail' not found");
    }

    // Calculate the total price
    long totalPrice = instructionDetailCount * servicePrice.getPrice();
    course.setStatus(ConstStatus.ACTIVE_STATUS);
    courseRepository.save(course);
    // Update the balance of the user's wallet
    WalletResponse wallet =
        walletService.findWalletByUserId(userId); // Assuming the first user in the company
    walletService.updateBalance(
        wallet.getId(), totalPrice, false, servicePrice.getId(), userId, courseId, null);
  }

  @Override
  public void delete(String id) {
    try {
      Course courseById = findById(id);
      courseRepository.deleteById(courseById.getId());
      if (checkCourseInUse(courseById)) {
        throw new RuntimeException("Course is in use, cannot delete");
      }
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

  private Boolean checkCourseInUse(Course course) {
    if (course.getNumberOfScan() > 0) {
      return true;
    }
    return false;
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
      courseResponse.setInstructions(instructionService.findByCourseId(courseById.getId()));
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
  public PagingModel<CourseResponse> findCourseNoMandatory(int page, int size, String companyId) {
    try {
      PagingModel<CourseResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      List<Course> coursesNoMandatory = courseRepository.findCourseNoMandatory(pageable, companyId);

      List<CourseResponse> courseResponses =
          coursesNoMandatory.stream()
              .map(
                  c -> {
                    CourseResponse courseResponse = CourseMapper.fromEntityToCourseResponse(c);
                    return courseResponse;
                  })
              .toList();

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
      throw new AppException(ErrorCode.FIND_COURSE_NO_MANDATORY_FAILED);
    }
  }

  @Override
  public CourseResponse findByCode(String courseCode) {
    try {
      Course courseByCode = courseRepository.findByCode(courseCode);

      return CourseMapper.fromEntityToCourseResponse(courseByCode);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
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
