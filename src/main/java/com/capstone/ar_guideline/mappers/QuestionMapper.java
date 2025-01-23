package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.entities.Question;
import com.capstone.ar_guideline.entities.Quiz;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {

  public static Question fromQuestionCreationRequestToEntity(QuestionCreationRequest request) {
    return Question.builder()
        .quiz(Quiz.builder().id(request.getQuizId()).build())
        .question(request.getQuestion())
        .build();
  }

  public static QuestionResponse fromEntityToQuestionResponse(Question question) {
    return QuestionResponse.builder()
        .id(question.getId())
        .quizId(question.getQuiz().getId())
        .question(question.getQuestion())
        .build();
  }
}
