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
import com.capstone.ar_guideline.repositories.*;
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
import org.springframework.data.domain.Page;
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

  WalletTransactionRepository walletTransactionRepository;
  CourseRepository courseRepository;
  UserRepository userRepository;
  RedisTemplate<String, Object> redisTemplate;
  ICompanyService companyService;
  IModelService modelService;
  IInstructionService instructionService;
  IMachineTypeService machineTypeService;
  InstructionDetailRepository instructionDetailRepository;
  ServicePricerRepository servicePricerRepository;
  WalletServiceImpl walletService;
  FirebaseNotificationServiceImpl firebaseNotificationService;

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

      // Send notification about new course
      String topic = "company_" + request.getCompanyId();
      String title = "New Course Available";
      String body = "A new course '" + newCourse.getTitle() + "' is now available";
      String data =
          "type:new_course,courseId:" + newCourse.getId() + ",courseName:" + newCourse.getTitle();

      try {
        firebaseNotificationService.sendNotificationToTopic(topic, title, body, data);
      } catch (Exception e) {
        // Log but don't fail the course creation if notification fails
        log.error("Failed to send course creation notification", e);
      }

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
  public List<Course> findByModelIdReturnList(String modelId) {
    try {
      return courseRepository.findByModelIdReturnList(modelId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
  }

  @Override
  public List<Course> findByModelId(String modelId, String companyId) {
    try {
      return courseRepository.findByModelIdAndCompanyId(modelId, companyId);
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
  public void updateNumberOfScan(String id, String userId) {
    Course course = findById(id);
    ServicePrice servicePrice = servicePricerRepository.findByName("Scan AR");
    course.setNumberOfScan(course.getNumberOfScan() + 1);
    courseRepository.save(course);
    User user = userRepository.findUserById(userId);
    walletService.updateBalance(
        user.getWallet().getId(),
        servicePrice.getPrice(),
        false,
        servicePrice.getId(),
        userId,
        id,
        null);
  }

  @Override
  @Transactional
  public void publishGuidelineFirstTime(String courseId, String userId) {
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

    // Count the number of InstructionDetail
    List<InstructionDetail> instructionDetailCount =
        instructionDetailRepository.countInstructionDetailByCourseId(courseId);

    if (instructionDetailCount.size() < 0) {
      throw new RuntimeException("Must have at lease 1 instruction detail");
    }

    // Find the ServicePrice with the name "Instruction Detail"
    ServicePrice servicePrice = servicePricerRepository.findByName("Create Guideline");
    if (servicePrice == null) {
      throw new RuntimeException("ServicePrice 'Instruction Detail' not found");
    }

    // Calculate the total price
    long totalPrice = instructionDetailCount.size() * servicePrice.getPrice();
    course.setStatus(ConstStatus.ACTIVE_STATUS);
    courseRepository.save(course);
    for (InstructionDetail instructionDetail : instructionDetailCount) {
      instructionDetail.setStatus(ConstStatus.ACTIVE_STATUS);
    }
    instructionDetailRepository.saveAll(instructionDetailCount);
    // Update the balance of the user's wallet
    WalletResponse wallet = walletService.findWalletByUserId(userId);
    walletService.updateBalance(
        wallet.getId(), totalPrice, false, servicePrice.getId(), userId, courseId, null);
  }

  @Override
  public Boolean isPaid(String id) {
    return walletTransactionRepository.isGuidelinePay(id);
  }

  @Override
  public List<Course> findByMachineTypeIdAndCompanyId(String machineTypeId, String companyId) {
    try {
      return courseRepository.findByMachineTypeIdAndCompanyId(machineTypeId, companyId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
    }
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
  public PagingModel<CourseResponse> findByCompanyId(
      int page, int size, String companyId, String title, String status, String machineTypeId) {
    try {
      PagingModel<CourseResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      companyService.findByIdReturnEntity(companyId);

      Page<Course> coursesByCompanyId =
          courseRepository.findByCompanyId(pageable, companyId, title, status, machineTypeId);

      List<CourseResponse> courseResponses =
          coursesByCompanyId.stream().map(CourseMapper::fromEntityToCourseResponse).toList();

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) coursesByCompanyId.getTotalElements());
      pagingModel.setTotalPages(coursesByCompanyId.getTotalPages());
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
