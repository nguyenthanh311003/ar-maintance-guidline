package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.services.IModelTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model-types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelTypeController {
    IModelTypeService modelTypeService;

    @PostMapping
    ApiResponse<ModelTypeResponse> createModelType(@RequestBody @Valid ModelTypeCreationRequest request) {
        return ApiResponse.<ModelTypeResponse>builder()
                .result(modelTypeService.create(request))
                .build();
    }

    @PutMapping("/{modelTypeId}")
    ApiResponse<ModelTypeResponse> updateModelType(@PathVariable String modelTypeId, @RequestBody ModelTypeCreationRequest request) {
        return ApiResponse.<ModelTypeResponse>builder()
                .result(modelTypeService.update(modelTypeId, request))
                .build();
    }

    @DeleteMapping("/{modelTypeId}")
    ApiResponse<String> deleteModelType(@PathVariable String modelTypeId) {
        modelTypeService.delete(modelTypeId);
        return ApiResponse.<String>builder().result("Model Type has been deleted").build();
    }
}
