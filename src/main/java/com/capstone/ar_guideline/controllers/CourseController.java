package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
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
  public ApiResponse<PagingModel<CourseResponse>> getAllCourses(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) boolean isEnrolled,
        @RequestParam(required = false) String userId,
      @RequestParam(required = false) String searchTemp,
      @RequestParam(required = false) String status) {
    return ApiResponse.<PagingModel<CourseResponse>>builder()
        .result(courseService.findAll(page, size,isEnrolled, userId, searchTemp, status))
        .build();
  }

    @GetMapping(value = ConstAPI.CourseAPI.COURSE+"/{courseId}")
    public ApiResponse<CourseResponse> getCourseById(@PathVariable String courseId) {
      return ApiResponse.<CourseResponse>builder()
              .result(courseService.findByIdResponse(courseId))
              .build();
    }
  @GetMapping(value = ConstAPI.CourseAPI.COURSE_FIND_BY_TITILE+"/{title}")
  public ApiResponse<CourseResponse> getCourseByTitle(@PathVariable String title) {
    return ApiResponse.<CourseResponse>builder()
            .result(courseService.findByTitleResponse(title))
            .build();
  }

  @PostMapping
  public ApiResponse<CourseResponse> createCourse(
      @RequestBody @Valid CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder().result(courseService.create(request)).build();
  }

  @PutMapping("/{courseId}")
  public ApiResponse<CourseResponse> updateCourse(
      @PathVariable String courseId, @RequestBody @Valid CourseCreationRequest request) {
    return ApiResponse.<CourseResponse>builder()
        .result(courseService.update(courseId, request))
        .build();
  }

  @DeleteMapping("/{courseId}")
  public ApiResponse<String> deleteCourse(@PathVariable String courseId) {
    courseService.delete(courseId);
    return ApiResponse.<String>builder().result("Course has been deleted successfully").build();
  }
}
