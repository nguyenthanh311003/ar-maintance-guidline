package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.AssignGuideline.AssignGuidelineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.AssignGuideline.AssignGuidelineResponse;
import com.capstone.ar_guideline.entities.AssignGuideline;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.User;

public class AssignGuidelineMapper {
  public static AssignGuideline fromCreationRequestToEntity(
      AssignGuidelineCreationRequest request) {
    return AssignGuideline.builder()
        .guideline(Course.builder().id(request.getGuidelineId()).build())
        .employee(User.builder().id(request.getEmployeeId()).build())
        .manager(User.builder().id(request.getManagerId()).build())
        .build();
  }

  public static AssignGuidelineResponse fromEntityToResponse(AssignGuideline assignGuideline) {
    return AssignGuidelineResponse.builder()
        .id(assignGuideline.getId())
        .guideline(CourseMapper.fromEntityToCourseResponse(assignGuideline.getGuideline()))
        .employee(UserMapper.fromEntityToUserResponse(assignGuideline.getEmployee()))
        .manager(UserMapper.fromEntityToUserResponse(assignGuideline.getManager()))
        .status(assignGuideline.getStatus())
        .createdDate(assignGuideline.getCreatedDate())
        .updatedDate(assignGuideline.getUpdatedDate())
        .build();
  }

  public static AssignGuideline fromResponseToEntity(AssignGuidelineResponse response) {
    return AssignGuideline.builder()
        .id(response.getId())
        .guideline(CourseMapper.fromCourseResponseToEntity(response.getGuideline()))
        .employee(UserMapper.fromUserResponseToEntity(response.getEmployee()))
        .manager(UserMapper.fromUserResponseToEntity(response.getManager()))
        .status(response.getStatus())
        .createdDate(response.getCreatedDate())
        .updatedDate(response.getUpdatedDate())
        .build();
  }
}
