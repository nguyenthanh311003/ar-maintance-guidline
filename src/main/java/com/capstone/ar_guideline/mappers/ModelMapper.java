package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Model;
import com.capstone.ar_guideline.entities.ModelType;

public class ModelMapper {
  public static Model fromModelCreationRequestToEntity(ModelCreationRequest request) {
    return Model.builder()
        .modelType(ModelType.builder().id(request.getModelTypeId()).build())
        .modelCode(request.getModelCode())
        .status(request.getStatus())
        .name(request.getName())
            .company(Company.builder().id(request.getCompanyId()).build())
        .description(request.getDescription())
        .version(request.getVersion())
        .scale(request.getScale())
        .build();
  }

  public static ModelResponse fromEntityToModelResponse(Model model) {
    return ModelResponse.builder()
        .id(model.getId())
        .modelTypeId(model.getModelType().getId())
        .modelCode(model.getModelCode())
        .status(model.getStatus())
        .name(model.getName())
            .companyId(model.getCompany().getId())
        .imageUrl(model.getImageUrl())
        .version(model.getVersion())
        .scale(model.getScale())
        .file(model.getFile())
        .build();
  }
}
