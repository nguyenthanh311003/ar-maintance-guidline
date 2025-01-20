package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Quiz.QuizCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.entities.Quiz;

public interface IQuizService {
  QuizResponse create(QuizCreationRequest request);

  QuizResponse update(String id, QuizCreationRequest request);

  void delete(String id);

  Quiz findById(String id);
}
