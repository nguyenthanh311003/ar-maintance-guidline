package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.entities.Question;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.QuestionMapper;
import com.capstone.ar_guideline.repositories.QuestionRepository;
import com.capstone.ar_guideline.services.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements IQuestionService {

  @Autowired private QuestionRepository quizRepository;

  @Autowired private QuestionMapper questionMapper;

  @Override
  public QuestionResponse createQuestion(QuestionCreationRequest questionCreationRequest) {
    try {

      Question question =
          QuestionMapper.fromQuestionCreationRequestToEntity(questionCreationRequest);

      question = quizRepository.save(question);

      return QuestionMapper.fromEntityToQuestionResponse(question);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_CREATE_FAILED);
    }
  }
}
