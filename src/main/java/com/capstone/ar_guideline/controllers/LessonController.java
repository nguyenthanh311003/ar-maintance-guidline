package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.services.ILessonService;
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
public class LessonController {

  ILessonService lessonService;

  //    @GetMapping
  //    public ApiResponse<PagingModel<LessonResponse>> getAllLessons(
  //            @RequestParam(defaultValue = "0") int page,
  //            @RequestParam(defaultValue = "10") int size,
  //            @RequestParam(required = false) String searchTemp) {
  //        log.info("Fetching lessons with page: {}, size: {}, searchTemp: {}", page, size,
  // searchTemp);
  //        return ApiResponse.<PagingModel<LessonResponse>>builder()
  //                .result(lessonService.findAll(page, size, searchTemp))
  //                .build();
  //    }

  //    @GetMapping("/{lessonId}")
  //    public ApiResponse<LessonResponse> getLessonById(@PathVariable String lessonId) {
  //        log.info("Fetching lesson with ID: {}", lessonId);
  //        return ApiResponse.<LessonResponse>builder()
  //                .result(lessonService.findById(lessonId))
  //                .build();
  //    }

  @PostMapping(value = ConstAPI.LessonAPI.LESSON)
  public ApiResponse<LessonResponse> createLesson(
      @RequestBody @Valid LessonCreationRequest request) {
    log.info("Creating a new lesson: {}", request);
    return ApiResponse.<LessonResponse>builder().result(lessonService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.LessonAPI.LESSON + "/{lessonId}")
  public ApiResponse<LessonResponse> updateLesson(
      @PathVariable String lessonId, @RequestBody @Valid LessonCreationRequest request) {
    log.info("Updating lesson with ID: {}", lessonId);
    return ApiResponse.<LessonResponse>builder()
        .result(lessonService.update(lessonId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.LessonAPI.LESSON + "/{lessonId}")
  public ApiResponse<String> deleteLesson(@PathVariable String lessonId) {
    log.info("Deleting lesson with ID: {}", lessonId);
    lessonService.delete(lessonId);
    return ApiResponse.<String>builder().result("Lesson has been deleted successfully").build();
  }
}
