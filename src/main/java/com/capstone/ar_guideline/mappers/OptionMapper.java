package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.entities.Option;
import com.capstone.ar_guideline.entities.Question;

public class OptionMapper {
  public static Option fromOptionCreationRequestToEntity(
      OptionCreationRequest request, Question question) {
    return Option.builder()
        .question(question)
        .option(request.getOption())
        .isRight(request.getIsRight())
        .build();
  }

  public static OptionResponse fromEntityToOptionResponse(Option option) {
    return OptionResponse.builder()
        .id(option.getId())
        .questionId(option.getQuestion().getId())
        .option(option.getOption())
        .isRight(option.getIsRight())
        .build();
  }
}
