package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.ModelLesson.ModelLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.ModelLesson.ModelLessonResponse;
import com.capstone.ar_guideline.services.IModelLessonService;
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
public class ModelLessonController {
    IModelLessonService modelLessonService;

    @PostMapping(value = ConstAPI.ModelLessonAPI.MODELLESSON)
    ApiResponse<ModelLessonResponse> createModelLesson(@RequestBody @Valid ModelLessonCreationRequest request) {
        return ApiResponse.<ModelLessonResponse>builder()
                .result(modelLessonService.create(request))
                .build();
    }

    @PutMapping(value = ConstAPI.ModelLessonAPI.MODELLESSON + "{modelLessonId}")
    ApiResponse<ModelLessonResponse> updateModelLesson(
            @PathVariable String modelLessonId, @RequestBody @Valid ModelLessonCreationRequest request) {
        return ApiResponse.<ModelLessonResponse>builder()
                .result(modelLessonService.update(modelLessonId, request))
                .build();
    }

    @DeleteMapping(value = ConstAPI.ModelLessonAPI.MODELLESSON + "{modelLessonId}")
    ApiResponse<String> deleteModelLesson(@PathVariable String modelLessonId) {
        modelLessonService.delete(modelLessonId);
        return ApiResponse.<String>builder().result("ModelLesson has been deleted").build();
    }
}
