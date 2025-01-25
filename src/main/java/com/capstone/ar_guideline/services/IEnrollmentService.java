package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.entities.Enrollment;

import java.util.List;

public interface IEnrollmentService {
  EnrollmentResponse create(EnrollmentCreationRequest request);

  EnrollmentResponse update(String id, EnrollmentCreationRequest request);

  EnrollmentResponse changeStatusToTrue(String id);

  void delete(String id);

  Enrollment findById(String id);

  Integer countByCourseId(String courseId);

  List<EnrollmentResponse> createAll(List<EnrollmentCreationRequest> requests);
}
