package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;

public class CourseMapper {
  public static Course fromCourseCreationRequestToEntity(CourseCreationRequest request) {
    return Course.builder()
        .id(request.getId())
        .title(request.getTitle())
        .duration(request.getDuration())
        .isMandatory(request.getIsMandatory())
        .company(Company.builder().id(request.getCompanyId()).build())
        .type(request.getType())
        .status(request.getStatus())
        .description(request.getDescription())
        .build();
  }

  public static CourseResponse fromEntityToCourseResponse(Course course) {
    return CourseResponse.builder()
        .id(course.getId())
        .companyId(course.getCompany().getId())
        // .lessons(course.getLessons())
        .title(course.getTitle())
        .description(course.getDescription())
        .duration(course.getDuration())
        .isMandatory(course.getIsMandatory())
        .status(course.getStatus())
        .type(course.getType())
        .build();
  }

  public static Course fromCourseResponseToEntity(CourseResponse response) {
    return Course.builder()
        .id(response.getId())
        // .lessons(response.getLessons())
        .title(response.getTitle())
        .description(response.getDescription())
        .company(Company.builder().id(response.getCompanyId()).build())
        .duration(response.getDuration())
        .status(response.getStatus())
        .isMandatory(response.getIsMandatory())
        .type(response.getType())
        .build();
  }
}
