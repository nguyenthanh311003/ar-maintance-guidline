package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Model;

public interface IModelService {
    ModelResponse create(ModelCreationRequest request);
    ModelResponse update(String id, ModelCreationRequest request);
    void delete(String id);
    Model findById(String id);
}
