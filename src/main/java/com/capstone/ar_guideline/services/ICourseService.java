package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Course;

public interface ICourseService {

  PagingModel<CourseResponse> findAll(int page, int size, boolean isEnrolled, String userId,String searchTemp, String status);

  CourseResponse create(CourseCreationRequest request);

  CourseResponse update(String id, CourseCreationRequest request);

  void delete(String id);

  Course findById(String id);
  CourseResponse findByIdResponse(String id);
}
