package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.ICourseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseController {

  ICourseService courseService;
  IARGuidelineService arGuidelineService;

  @GetMapping(value = ConstAPI.CourseAPI.COURSE)
  public ApiResponse<PagingModel<CourseResponse>> getAllCourses(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) boolean isEnrolled,
      @RequestParam(required = false) Boolean isMandatory,
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String searchTemp,
      @RequestParam(required = false) String companyId,
      @RequestParam(required = false) String status) {
    return ApiResponse.<PagingModel<CourseResponse>>builder()
        .result(
            courseService.findAll(
                page, size, isEnrolled, isMandatory, userId, searchTemp, companyId, status))
        .build();
  }

  @GetMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  public ApiResponse<CourseResponse> getCourseById(@PathVariable String courseId) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.findByIdResponse(courseId))
        .build();
  }

  @GetMapping(value = ConstAPI.CourseAPI.FIND_COURSE_BY_CODE + "{courseCode}")
  public ApiResponse<CourseResponse> getByCourseCode(@PathVariable String courseCode) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.findByCode(courseCode))
        .build();
  }

  @GetMapping(value = ConstAPI.CourseAPI.COURSE_FIND_BY_COMPANY_ID + "{companyId}")
  public ApiResponse<List<CourseResponse>> getCourseByCompanyId(@PathVariable String companyId) {
    return ApiResponse.<List<CourseResponse>>builder()
        .result(courseService.findByCompanyId(companyId))
        .build();
  }

  @GetMapping(value = ConstAPI.CourseAPI.NO_MANDATORY_COURSE + "{companyId}")
  public ApiResponse<PagingModel<CourseResponse>> getNoMandatoryCourse(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<CourseResponse>>builder()
        .result(courseService.findCourseNoMandatory(page, size, companyId))
        .build();
  }

  @GetMapping(value = ConstAPI.CourseAPI.COURSE_FIND_BY_TITLE + "/{title}")
  public ApiResponse<CourseResponse> getCourseByTitle(@PathVariable String title) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.findByTitleResponse(title))
        .build();
  }

  @PostMapping(value = ConstAPI.CourseAPI.COURSE)
  public ApiResponse<CourseResponse> createCourse(
      @ModelAttribute @Valid CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder()
        .result(arGuidelineService.createGuideline(request))
        .build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  public ApiResponse<CourseResponse> updateCourse(
      @PathVariable String courseId, @ModelAttribute @Valid CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.update(courseId, request))
        .build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.CHANGE_STATUS_GUIDELINE + "{courseId}")
  public ApiResponse<String> changeStatusByCourseId(@PathVariable String courseId) {
    arGuidelineService.changeStatusCourse(courseId);
    return ApiResponse.<String>builder()
        .result("Course status has been changed successfully")
        .build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.UPDATE_COURSE_PICTURE + "/{courseId}")
  public ApiResponse<String> updateCoursePicture(
      @PathVariable String courseId, @RequestParam MultipartFile file) {
    return ApiResponse.<String>builder()
        .result(courseService.updateCoursePicture(courseId, file))
        .build();
  }

  @DeleteMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  public ApiResponse<String> deleteCourse(@PathVariable String courseId) {
    arGuidelineService.deleteCourseById(courseId);
    return ApiResponse.<String>builder().result("Course has been deleted successfully").build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.UPDATE_COURSE_SCAN_TIMES + "/{courseId}")
  public ApiResponse<String> updateScanTimes(@PathVariable String courseId) {
    courseService.updateNumberOfScan(courseId);
    return ApiResponse.<String>builder().result("Course has been updated successfully").build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.PUBLIC_GUIDELINE_FIRST_TIME + "/{courseId}")
  public ApiResponse<String> publicGuidelineFirstTime(
      @PathVariable String courseId, @RequestParam(name = "userId") String userId) {
    courseService.publishGuidelineFirstTime(courseId, userId);
    return ApiResponse.<String>builder()
        .result("Course has been published and wallet balance updated successfully")
        .build();
  }
}
