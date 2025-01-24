package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Quiz.QuizCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.services.IQuizService;
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
public class QuizController {
  IQuizService quizService;

  @GetMapping(value = ConstAPI.QuizAPI.FIND_QUIZ_BY_COURSE_ID + "{courseId}")
  ApiResponse<QuizResponse> find(@PathVariable String courseId) {
    return ApiResponse.<QuizResponse>builder().result(quizService.findByCourseId(courseId)).build();
  }

  @PostMapping(value = ConstAPI.QuizAPI.CREATE_QUIZ)
  ApiResponse<QuizResponse> createQuiz(@RequestBody @Valid QuizCreationRequest request) {
    return ApiResponse.<QuizResponse>builder().result(quizService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.QuizAPI.UPDATE_QUIZ + "{quizId}")
  ApiResponse<QuizResponse> updateQuiz(
      @PathVariable String quizId, @RequestBody QuizCreationRequest request) {
    return ApiResponse.<QuizResponse>builder().result(quizService.update(quizId, request)).build();
  }

  @DeleteMapping(value = ConstAPI.QuizAPI.DELETE_QUIZ + "{quizId}")
  ApiResponse<String> deleteQuiz(@PathVariable String quizId) {
    quizService.delete(quizId);
    return ApiResponse.<String>builder().result("Quiz has been deleted").build();
  }
}
