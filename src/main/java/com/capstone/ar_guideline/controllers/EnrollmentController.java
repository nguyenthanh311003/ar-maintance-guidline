package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IEnrollmentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EnrollmentController {
  IEnrollmentService enrollmentService;

  @PostMapping(value = ConstAPI.EnrollmentAPI.CREATE_ENROLLMENT)
  ApiResponse<List<EnrollmentResponse>> createEnrollment(
      @RequestBody @Valid List<EnrollmentCreationRequest> request) {
    return ApiResponse.<List<EnrollmentResponse>>builder()
        .result(enrollmentService.createAll(request))
        .build();
  }

  @GetMapping(value = ConstAPI.EnrollmentAPI.FIND_COURSE_MANDATORY + "{userId}")
  ApiResponse<PagingModel<EnrollmentResponse>> findCourseMandatory(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable String userId,
      @RequestParam Boolean isRequiredCourse) {
    return ApiResponse.<PagingModel<EnrollmentResponse>>builder()
        .result(enrollmentService.findCourseIsRequiredForUser(page, size, userId, isRequiredCourse))
        .build();
  }

  @PutMapping(value = ConstAPI.EnrollmentAPI.UPDATE_ENROLLMENT + "{enrollmentId}")
  ApiResponse<EnrollmentResponse> updateEnrollment(
      @PathVariable String enrollmentId, @RequestBody EnrollmentCreationRequest request) {
    return ApiResponse.<EnrollmentResponse>builder()
        .result(enrollmentService.update(enrollmentId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.EnrollmentAPI.DELETE_ENROLLMENT + "{enrollmentId}")
  ApiResponse<String> deleteEnrollment(@PathVariable String enrollmentId) {
    enrollmentService.delete(enrollmentId);
    return ApiResponse.<String>builder().result("Enrollment has been deleted").build();
  }

  @PutMapping(value = ConstAPI.EnrollmentAPI.UPDATE_STATUS_ENROLLMENT + "{enrollmentId}")
  ApiResponse<EnrollmentResponse> updateEnrollmentStatus(@PathVariable String enrollmentId) {
    return ApiResponse.<EnrollmentResponse>builder()
        .result(enrollmentService.changeStatusToTrue(enrollmentId))
        .build();
  }

  @PutMapping(value = ConstAPI.EnrollmentAPI.ENROLL)
  void enroll(@RequestBody EnrollmentCreationRequest request) {
    enrollmentService.enroll(request.getCourseId(), request.getUserId());
  }
}
