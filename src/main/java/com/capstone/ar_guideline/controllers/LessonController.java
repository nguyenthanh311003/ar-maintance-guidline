package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.services.ILessonService;
import jakarta.validation.Valid;
import java.util.List;
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

  @GetMapping(value = ConstAPI.LessonAPI.LESSON + "/course/{courseId}")
  public ApiResponse<List<LessonResponse>> getAllByCourseId(@PathVariable String courseId) {
    log.info("get all by course id: {}", courseId);
    return ApiResponse.<List<LessonResponse>>builder()
        .result(lessonService.findByCourseId(courseId))
        .build();
  }

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

  @PutMapping(value = ConstAPI.LessonAPI.LESSON + "/swap")
  public ApiResponse<String> swapOrder(@RequestParam String id1, @RequestParam String id2) {
    log.info("Swapping order of lessons with ID: {} and {}", id1, id2);
    lessonService.swapOrder(id1, id2);
    return ApiResponse.<String>builder()
        .result("Lesson order has been swapped successfully")
        .build();
  }
}
