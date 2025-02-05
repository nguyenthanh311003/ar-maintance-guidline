package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.entities.Enrollment;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface IEnrollmentService {
  EnrollmentResponse create(EnrollmentCreationRequest request);

  EnrollmentResponse update(String id, EnrollmentCreationRequest request);

  EnrollmentResponse changeStatusToTrue(String id);

  void delete(String id);

  Enrollment findById(String id);

  List<EnrollmentResponse> createAll(List<EnrollmentCreationRequest> requests);

  List<EnrollmentResponse> findCourseIsRequiredForUser(String userId, Boolean isMandatory);

  boolean checkUserIsAssign(String userId, String courseId);

  List<Enrollment> findByCourseId(String courseId);

  @Query(
      "SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId AND e.enrollmentDate IS NOT NULL")
  Integer countByCourseIdAndEnrollmentDateNotNull(String courseId);
}
