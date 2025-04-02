package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IModelService;
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
public class ModelController {
  IModelService modelService;
  IARGuidelineService arGuidelineService;

  @GetMapping(value = ConstAPI.ModelAPI.GET_MODEL_BY_ID + "{modelId}")
  ApiResponse<ModelResponse> getModelById(@PathVariable String modelId) {
    return ApiResponse.<ModelResponse>builder()
        .result(arGuidelineService.findModelById(modelId))
        .build();
  }

  @GetMapping(value = ConstAPI.ModelAPI.GET_UNUSED_MODEL_BY_ID + "{companyId}")
  ApiResponse<List<ModelResponse>> getUnusedModelByCompanyId(@PathVariable String companyId) {
    return ApiResponse.<List<ModelResponse>>builder()
        .result(modelService.getModelUnused(companyId))
        .build();
  }

  @GetMapping(value = ConstAPI.ModelAPI.GET_MODEL_BY_COURSE + "{courseId}")
  ApiResponse<ModelResponse> getByCourseId(@PathVariable String courseId) {
    return ApiResponse.<ModelResponse>builder()
        .result(modelService.getByCourseId(courseId))
        .build();
  }

  @GetMapping(value = ConstAPI.ModelAPI.GET_MODEL_BY_COMPANY_ID + "{companyId}")
  ApiResponse<PagingModel<ModelResponse>> getModelByCompanyId(
      @PathVariable String companyId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") String type,
      @RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "") String code) {
    return ApiResponse.<PagingModel<ModelResponse>>builder()
        .result(arGuidelineService.findModelByCompanyId(page, size, companyId, type, name, code))
        .build();
  }

  @PostMapping(value = ConstAPI.ModelAPI.CREATE_MODEL)
  ApiResponse<ModelResponse> createModel(@ModelAttribute ModelCreationRequest request)
      throws InterruptedException {
    return ApiResponse.<ModelResponse>builder().result(modelService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.ModelAPI.UPDATE_MODEL + "{modelId}")
  ApiResponse<ModelResponse> updateModel(
      @PathVariable String modelId, @ModelAttribute @Valid ModelCreationRequest request) {
    return ApiResponse.<ModelResponse>builder()
        .result(arGuidelineService.updateModel(modelId, request))
        .build();
  }

//  @DeleteMapping(value = ConstAPI.ModelAPI.DELETE_MODEL + "{modelId}")
//  ApiResponse<String> deleteModel(@PathVariable String modelId) {
//    arGuidelineService.deleteModelById(modelId);
//    return ApiResponse.<String>builder().result("Model has been deleted").build();
//  }

  @DeleteMapping(value = ConstAPI.ModelAPI.UPDATE_MODEL + "{modelId}")
  ApiResponse<String> changeStatusModel(@PathVariable String modelId) {
    modelService.changeStatus(modelId);
    return ApiResponse.<String>builder().result("Model status has been changed").build();
  }
}
