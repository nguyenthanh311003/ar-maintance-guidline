package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Model;
import java.util.List;

public interface IModelService {
  ModelResponse create(ModelCreationRequest request) throws InterruptedException;

  ModelResponse update(String id, ModelCreationRequest request);

  void delete(String id);

  Model findById(String id);

  PagingModel<ModelResponse> findByCompanyId(
      int page, int size, String companyId, String type, String name, String code);

  List<ModelResponse> getModelUnused(String companyId);
}
