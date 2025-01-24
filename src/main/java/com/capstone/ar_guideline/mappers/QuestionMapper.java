package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.entities.Question;
import com.capstone.ar_guideline.entities.Quiz;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {

  public static Question fromQuestionCreationRequestToEntity(
      QuestionCreationRequest request, Quiz quiz) {
    return Question.builder().quiz(quiz).question(request.getQuestion()).build();
  }

  public static QuestionResponse fromEntityAndOptionResponsesToQuestionResponse(
      Question question, List<OptionResponse> optionResponses) {
    return QuestionResponse.builder()
        .id(question.getId())
        .quizId(question.getQuiz().getId())
        .optionResponses(optionResponses)
        .question(question.getQuestion())
        .build();
  }
}
