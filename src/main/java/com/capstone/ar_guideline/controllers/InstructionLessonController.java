package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.InstructionLesson.InstructionLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionLesson.InstructionLessonResponse;
import com.capstone.ar_guideline.services.IInstructionLessonService;
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
public class InstructionLessonController {
  IInstructionLessonService instructionLessonService;

  @PostMapping(value = ConstAPI.InstructionLessonAPI.INSTRUCTION_LESSON)
  ApiResponse<InstructionLessonResponse> createModelLesson(
      @RequestBody @Valid InstructionLessonCreationRequest request) {
    return ApiResponse.<InstructionLessonResponse>builder()
        .result(instructionLessonService.create(request))
        .build();
  }

  @PutMapping(value = ConstAPI.InstructionLessonAPI.INSTRUCTION_LESSON + "{id}")
  ApiResponse<InstructionLessonResponse> updateModelLesson(
      @PathVariable String modelLessonId, @RequestBody @Valid InstructionLessonCreationRequest request) {
    return ApiResponse.<InstructionLessonResponse>builder()
        .result(instructionLessonService.update(modelLessonId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.InstructionLessonAPI.INSTRUCTION_LESSON + "{id}")
  ApiResponse<String> deleteModelLesson(@PathVariable String id) {
    instructionLessonService.delete(id);
    return ApiResponse.<String>builder().result("ModelLesson has been deleted").build();
  }

}
