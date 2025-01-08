package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Enrollment.EnrollmentCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Enrollment.EnrollmentResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Enrollment;
import com.capstone.ar_guideline.entities.User;

public class EnrollmentMapper {
  public static Enrollment fromEnrolmentCreationRequestToEntity(
      EnrollmentCreationRequest request, Course course, User user) {
    return Enrollment.builder().course(course).user(user).build();
  }

  public static EnrollmentResponse FromEntityToEnrollmentResponse(Enrollment enrollment) {
    return EnrollmentResponse.builder()
        .id(enrollment.getId())
        .courseId(enrollment.getCourse().getId())
        .userId(enrollment.getUser().getId())
        .isCompleted(enrollment.getIsCompleted())
        .completionDate(enrollment.getCompletionDate())
        .build();
  }
}
