package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;

public interface IModelTypeService {
    ModelTypeResponse create(ModelTypeCreationRequest request);
    ModelTypeResponse update(String id, ModelTypeCreationRequest request);
    void delete(String id);
}
