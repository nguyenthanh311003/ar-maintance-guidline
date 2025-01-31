package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Enrollment;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.EnrollmentMapper;
import com.capstone.ar_guideline.repositories.EnrollmentRepository;
import com.capstone.ar_guideline.services.IEnrollmentService;
import com.capstone.ar_guideline.services.IUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EnrollmentServiceImpl implements IEnrollmentService {
  EnrollmentRepository enrollmentRepository;

  @Autowired @Lazy MiddleCourseServiceImpl middleService;

  IUserService userService;

  @Override
  public EnrollmentResponse create(EnrollmentCreationRequest request) {
    try {
      Course course = middleService.findById(request.getCourseId());

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

      Course courseById = middleService.findById(request.getCourseId());

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
  public Integer countByCourseId(String courseId) {
    return enrollmentRepository.countByCourseId(courseId);
  }

  @Override
  public List<EnrollmentResponse> createAll(List<EnrollmentCreationRequest> requests) {

    try {
      List<EnrollmentResponse> responses = new ArrayList<>();
      for (EnrollmentCreationRequest request : requests) {
        responses.add(create(request));
      }
      return responses;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ENROLLMENT_CREATE_FAILED);
    }
  }
}
