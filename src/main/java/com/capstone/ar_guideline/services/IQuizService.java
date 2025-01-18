package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.entities.Quiz;

public interface IQuizService {
    QuizResponse create(Quiz request);

    ModelTypeResponse update(String id, ModelTypeCreationRequest request);

    void delete(String id);

    ModelType findById(String id);
}
