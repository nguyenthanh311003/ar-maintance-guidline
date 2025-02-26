package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.ModelType;

public interface IModelTypeService {
  PagingModel<ModelTypeResponse> getAll(int page, int size);

  ModelTypeResponse create(ModelTypeCreationRequest request);

  ModelTypeResponse update(String id, ModelTypeCreationRequest request);

  void delete(String id);

  ModelType findById(String id);
}
