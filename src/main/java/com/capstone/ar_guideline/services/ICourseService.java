package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {

  PagingModel<CourseResponse> findAll(
      int page,
      int size,
      boolean isEnrolled,
      Boolean isMandatory,
      String userId,
      String searchTemp,
      String companyId,
      String status);

  CourseResponse create(CourseCreationRequest request);

  CourseResponse update(String id, CourseCreationRequest request);

  void delete(String id);

  Course findById(String id);

  CourseResponse findByIdResponse(String id);

  CourseResponse findByTitleResponse(String title);

  List<CourseResponse> findByCompanyId(String companyId);

  PagingModel<CourseResponse> findCourseNoMandatory(int page, int size, String companyId);

  Course save(Course course);

  String updateCoursePicture(String courseId, MultipartFile file);

  }
