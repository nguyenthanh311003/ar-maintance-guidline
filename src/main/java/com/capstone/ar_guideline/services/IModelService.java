package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Model;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModelService {
  ModelResponse create(ModelCreationRequest request) throws InterruptedException;

  Model update(Model model);

  ModelResponse update(String id, ModelCreationRequest request);

  void delete(String id);

  Model findById(String id);

  Page<Model> findByCompanyId(
      Pageable pageable, String companyId, String type, String name, String code);

  List<ModelResponse> getModelUnused(String companyId);

  Boolean updateIsUsed(boolean isCreate, Model model);

  ModelResponse getByCourseId(String courseId);

  ModelResponse findByIdResponse(String id);

  List<Model> findAllByCompanyId(String companyId);

  void updateIsUsedByCourseId(String modelId);
}
