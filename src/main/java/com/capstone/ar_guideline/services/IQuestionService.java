package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;

public interface IQuestionService {

  QuestionResponse createQuestion(QuestionCreationRequest questionCreationRequest);
}
