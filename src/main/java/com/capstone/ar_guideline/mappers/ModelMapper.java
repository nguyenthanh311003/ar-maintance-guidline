package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Model;
import com.capstone.ar_guideline.entities.ModelType;

public class ModelMapper {
    public static Model fromModelCreationRequestToEntity(ModelCreationRequest request, ModelType modelType) {
        return Model.builder()
                .modelType(modelType)
                .modelCode(request.getModelCode())
                .status(request.getStatus())
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .documentUrl(request.getDocumentUrl())
                .aRUrl(request.getARUrl())
                .version(request.getVersion())
                .rotation(request.getRotation())
                .scale(request.getScale())
                .fileType(request.getFileType())
                .fileSize(request.getFileSize())
                .build();
    }

    public static ModelResponse fromEntityToModelResponse(Model model) {
        return ModelResponse.builder()
                .id(model.getId())
                .modelTypeId(model.getModelType().getId())
                .modelCode(model.getModelCode())
                .status(model.getStatus())
                .name(model.getName())
                .description(model.getDescription())
                .image(model.getImage())
                .documentUrl(model.getDocumentUrl())
                .aRUrl(model.getARUrl())
                .version(model.getVersion())
                .rotation(model.getRotation())
                .scale(model.getScale())
                .fileType(model.getFileType())
                .fileSize(model.getFileSize())
                .build();
    }

    public static Model fromModelResponseToEntity(ModelResponse response, ModelType modelType) {
        return Model.builder()
                .id(response.getId())
                .modelType(modelType)
                .modelCode(response.getModelCode())
                .status(response.getStatus())
                .name(response.getName())
                .description(response.getDescription())
                .image(response.getImage())
                .documentUrl(response.getDocumentUrl())
                .aRUrl(response.getARUrl())
                .version(response.getVersion())
                .rotation(response.getRotation())
                .scale(response.getScale())
                .fileType(response.getFileType())
                .fileSize(response.getFileSize())
                .build();
    }
}
