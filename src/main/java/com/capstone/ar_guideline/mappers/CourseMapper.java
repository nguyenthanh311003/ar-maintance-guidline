package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Model;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
  public static Course fromCourseCreationRequestToEntity(CourseCreationRequest request) {
    return Course.builder()
        .title(request.getTitle())
        .duration(request.getDuration())
        .isMandatory(request.getIsMandatory())
        .shortDescription(request.getShortDescription())
        .targetAudience(request.getTargetAudience())
        .imageUrl(request.getImageUrlString())
        .company(Company.builder().id(request.getCompanyId()).build())
        .model(Model.builder().id(request.getModelId()).build())
        .type(request.getType())
        .status(request.getStatus())
        .description(request.getDescription())
        .build();
  }

  public static CourseResponse fromEntityToCourseResponse(Course course) {
    return CourseResponse.builder()
        .id(course.getId())
        .companyId(course.getCompany().getId())
        .modelId(course.getModel().getId())
        .title(course.getTitle())
        .description(course.getDescription())
        .shortDescription(course.getShortDescription())
        .targetAudience(course.getTargetAudience())
        .duration(course.getDuration())
        .numberOfScan(course.getNumberOfScan())
        .courseCode(course.getCourseCode())
        .imageUrl(course.getImageUrl())
        .isMandatory(course.getIsMandatory())
        .qrCode(course.getQrCode())
        .status(course.getStatus())
        .type(course.getType())
        .machineType(course.getModelType().getName())
        .modelName(course.getModel().getName())
        .machineTypeId(course.getModelType().getId())
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
        .imageUrl(response.getImageUrl())
        .status(response.getStatus())
        .isMandatory(response.getIsMandatory())
        .type(response.getType())
        .build();
  }
}
