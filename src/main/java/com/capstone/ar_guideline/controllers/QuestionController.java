package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Question.QuestionModifyRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.services.IQuestionService;
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
public class QuestionController {

  IQuestionService questionService;

  @PostMapping(value = ConstAPI.QuestionAPI.QUESTION)
  ApiResponse<QuestionResponse> create(@RequestBody @Valid QuestionCreationRequest request) {
    return ApiResponse.<QuestionResponse>builder()
        .result(questionService.createQuestion(request))
        .build();
  }

  @PutMapping(value = ConstAPI.QuestionAPI.UPDATE_QUESTION + "{questionId}")
  ApiResponse<QuestionResponse> updateInstructionDetail(
      @PathVariable String questionId, @RequestBody QuestionModifyRequest request) {
    return ApiResponse.<QuestionResponse>builder()
        .result(questionService.update(questionId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.QuestionAPI.DELETE_QUESTION + "{questionId}")
  ApiResponse<String> deleteInstructionDetail(@PathVariable String questionId) {
    questionService.delete(questionId);
    return ApiResponse.<String>builder().result("Question has been deleted").build();
  }
}
