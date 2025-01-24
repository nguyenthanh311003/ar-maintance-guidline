package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Question.QuestionModifyRequest;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.entities.Question;

public interface IQuestionService {

  QuestionResponse createQuestion(QuestionCreationRequest questionCreationRequest);

  QuestionResponse update(String id, QuestionModifyRequest questionModifyRequest);

  void delete(String id);

  Question findById(String id);
}
