package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Course.CourseListRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.ICourseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseController {
  ICourseService courseService;

  @GetMapping(value = ConstAPI.CourseAPI.COURSE)
  ApiResponse<PagingModel<CourseResponse>> getAllCourses(@RequestBody CourseListRequest request) {
    return ApiResponse.<PagingModel<CourseResponse>>builder()
        .result(
            courseService.findAll(
                request.getPage(), request.getSize(), request.getSearchTemp(), request.getStatus()))
        .build();
  }

  //    @GetMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  //    ApiResponse<CourseResponse> getCourse(@PathVariable String courseId) {
  //        return ApiResponse.<CourseResponse>builder()
  //                .result(courseService.findById(courseId))
  //                .build();
  //    }

  @PostMapping(value = ConstAPI.CourseAPI.COURSE)
  ApiResponse<CourseResponse> createCourse(@RequestBody @Valid CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder().result(courseService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  ApiResponse<CourseResponse> updateCourse(
      @PathVariable String courseId, @RequestBody CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.update(courseId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.CourseAPI.COURSE + "/{courseId}")
  ApiResponse<String> deleteCourse(@PathVariable String courseId) {
    courseService.delete(courseId);
    return ApiResponse.<String>builder().result("Course has been deleted").build();
  }
}
