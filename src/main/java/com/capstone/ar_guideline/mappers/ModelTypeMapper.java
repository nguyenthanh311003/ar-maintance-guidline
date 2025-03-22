package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.entities.ModelType;

public class ModelTypeMapper {
  public static ModelType fromModelTypeCreationRequestToEntity(ModelTypeCreationRequest request) {
    return ModelType.builder()
        .name(request.getName())
        .image(request.getImage())
        .description(request.getDescription())
        .build();
  }

  public static ModelTypeResponse fromEntityToModelTypeResponse(ModelType modelType) {
    return ModelTypeResponse.builder()
        .id(modelType.getId())
        .name(modelType.getName())
        .image(modelType.getImage())
        .description(modelType.getDescription())
        .company(
            modelType.getCompany() == null
                ? null
                : CompanyMapper.fromEntityToCompanyResponse(modelType.getCompany()))
        .build();
  }

  public static ModelType fromModelTypeResponseToEntity(ModelTypeResponse response) {
    return ModelType.builder()
        .id(response.getId())
        .name(response.getName())
        .image(response.getImage())
        .description(response.getDescription())
        .company(
            response.getCompany() == null
                ? null
                : CompanyMapper.fromCompanyResponseToEntity(response.getCompany()))
        .build();
  }
}
