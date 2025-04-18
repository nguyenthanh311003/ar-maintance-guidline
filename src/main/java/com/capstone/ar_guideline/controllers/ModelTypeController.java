package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IModelTypeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelTypeController {
  IModelTypeService modelTypeService;

  @GetMapping(value = ConstAPI.ModelTypeAPI.GET_ALL_MODEL_TYPE)
  ApiResponse<PagingModel<ModelTypeResponse>> getALl(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size) {
    return ApiResponse.<PagingModel<ModelTypeResponse>>builder()
        .result(modelTypeService.getAll(page, size))
        .build();
  }

  @GetMapping(value = ConstAPI.ModelTypeAPI.GET_MODEL_TYPE_BY_COMPANY + "{companyId}")
  ApiResponse<List<ModelTypeResponse>> getByCompanyId(@PathVariable String companyId) {
    return ApiResponse.<List<ModelTypeResponse>>builder()
        .result(modelTypeService.getModelTypeByCompanyId(companyId))
        .build();
  }

  @PostMapping(value = ConstAPI.ModelTypeAPI.CREATE_MODEL_TYPE)
  ApiResponse<ModelTypeResponse> createModelType(
      @RequestBody @Valid ModelTypeCreationRequest request) {
    return ApiResponse.<ModelTypeResponse>builder()
        .result(modelTypeService.create(request))
        .build();
  }

  @PutMapping(value = ConstAPI.ModelTypeAPI.UPDATE_MODEL_TYPE + "{modelTypeId}")
  ApiResponse<ModelTypeResponse> updateModelType(
      @PathVariable String modelTypeId, @RequestBody ModelTypeCreationRequest request) {
    return ApiResponse.<ModelTypeResponse>builder()
        .result(modelTypeService.update(modelTypeId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.ModelTypeAPI.DELETE_MODEL_TYPE + "{modelTypeId}")
  ApiResponse<String> deleteModelType(@PathVariable String modelTypeId) {
    modelTypeService.delete(modelTypeId);
    return ApiResponse.<String>builder().result("Model Type has been deleted").build();
  }
}
