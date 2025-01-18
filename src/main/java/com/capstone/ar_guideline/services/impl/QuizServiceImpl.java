package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.entities.Quiz;
import com.capstone.ar_guideline.services.IQuizService;

public class QuizServiceImpl implements IQuizService {
    
    @Override
    public QuizResponse create(Quiz request) {
   return  null;
    }

    @Override
    public ModelTypeResponse update(String id, ModelTypeCreationRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ModelType findById(String id) {
        return null;
    }
}
