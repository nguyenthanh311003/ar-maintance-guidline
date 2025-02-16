package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.services.IModelService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelController {
  IModelService modelService;

  @PostMapping(value = ConstAPI.ModelAPI.CREATE_MODEL)
  ApiResponse<ModelResponse> createModel(@ModelAttribute @Valid ModelCreationRequest request)
      throws InterruptedException {
    return ApiResponse.<ModelResponse>builder().result(modelService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.ModelAPI.UPDATE_MODEL + "{modelId}")
  ApiResponse<ModelResponse> updateModel(
      @PathVariable String modelId, @RequestBody ModelCreationRequest request) {
    return ApiResponse.<ModelResponse>builder()
        .result(modelService.update(modelId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.ModelAPI.DELETE_MODEL + "{modelId}")
  ApiResponse<String> deleteModel(@PathVariable String modelId) {
    modelService.delete(modelId);
    return ApiResponse.<String>builder().result("Model has been deleted").build();
  }
}
