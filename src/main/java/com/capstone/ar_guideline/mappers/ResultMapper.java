package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Result.ResultCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Result.ResultResponse;
import com.capstone.ar_guideline.entities.Quiz;
import com.capstone.ar_guideline.entities.Result;
import com.capstone.ar_guideline.entities.User;

public class ResultMapper {
  public static Result fromResultCreationRequestToEntity(
      ResultCreationRequest request, Quiz quiz, User user) {
    return Result.builder().user(user).quiz(quiz).build();
  }

  public static ResultResponse fromEntityToResultResponse(Result result) {
    return ResultResponse.builder()
        .id(result.getId())
        .quizId(result.getQuiz().getId())
        .userId(result.getUser().getId())
        .build();
  }
}
