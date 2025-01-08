package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.LessonProcess.LessonProcessResponse;
import com.capstone.ar_guideline.services.ILessonProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LessonProcessController {

  private final ILessonProcessService lessonProcessService;

  // Endpoint to create a new LessonProcess
  @PostMapping(value = ConstAPI.LessonProcessAPI.LESSON_PROCESS)
  public ApiResponse<LessonProcessResponse> createLessonProcess(
      @RequestBody @Valid LessonProcessCreationRequest request) {
    LessonProcessResponse response = lessonProcessService.create(request);
    return ApiResponse.<LessonProcessResponse>builder().result(response).build();
  }

  // Endpoint to update an existing LessonProcess
  @PutMapping(value = ConstAPI.LessonProcessAPI.LESSON_PROCESS + "/{id}")
  public ApiResponse<LessonProcessResponse> updateLessonProcess(
      @PathVariable String id, @RequestBody @Valid LessonProcessCreationRequest request) {
    LessonProcessResponse response = lessonProcessService.update(id, request);
    return ApiResponse.<LessonProcessResponse>builder().result(response).build();
  }

  // Endpoint to delete a LessonProcess by id
  @DeleteMapping(ConstAPI.LessonProcessAPI.LESSON_PROCESS + "/{id}")
  public ApiResponse<String> deleteLessonProcess(@PathVariable String id) {
    lessonProcessService.delete(id);
    return ApiResponse.<String>builder().result("Lesson Process has been deleted").build();
  }

  //    // Endpoint to get a LessonProcess by id
  //    @GetMapping("/{id}")
  //    public ApiResponse<LessonProcessResponse> getLessonProcess(@PathVariable String id) {
  //        LessonProcessResponse response = lessonProcessService.findById(id);
  //        return ApiResponse.<LessonProcessResponse>builder()
  //                .result(response)
  //                .build();
  //    }
}
