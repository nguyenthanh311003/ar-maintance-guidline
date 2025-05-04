package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Course;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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

  PagingModel<CourseResponse> findByCompanyId(
      int page, int size, String companyId, String title, String status, String machineTypeId,String staffId);

  PagingModel<CourseResponse> findCourseNoMandatory(int page, int size, String companyId);

  CourseResponse findByCode(String courseCode);

  Course save(Course course);

  String updateCoursePicture(String courseId, MultipartFile file);

  Course findByModelId(String modelId);

  List<Course> findByModelIdReturnList(String modelId);

  List<Course> findByModelId(String modelId, String companyId);

  void changeStatusByCourseId(String courseId);

  void updateNumberOfScan(String id, String userId);

  void publishGuidelineFirstTime(String courseId, String userId);

  Boolean isPaid(String id);

  List<Course> findByMachineTypeIdAndCompanyId(String machineTypeId, String companyId);
}
