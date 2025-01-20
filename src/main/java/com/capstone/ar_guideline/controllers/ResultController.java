package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Result.ResultCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Result.ResultResponse;
import com.capstone.ar_guideline.services.IResultService;
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
public class ResultController {
  IResultService resultService;

  @PostMapping(value = ConstAPI.ResultAPI.CREATE_RESULT)
  ApiResponse<ResultResponse> createResult(@RequestBody @Valid ResultCreationRequest request) {
    return ApiResponse.<ResultResponse>builder().result(resultService.create(request)).build();
  }

  @PutMapping(value = ConstAPI.ResultAPI.UPDATE_RESULT + "{resultId}")
  ApiResponse<ResultResponse> updateResult(
      @PathVariable String resultId, @RequestBody ResultCreationRequest request) {
    return ApiResponse.<ResultResponse>builder()
        .result(resultService.update(resultId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.ResultAPI.DELETE_RESULT + "{resultId}")
  ApiResponse<String> deleteResult(@PathVariable String resultId) {
    resultService.delete(resultId);
    return ApiResponse.<String>builder().result("Result has been deleted").build();
  }
}
