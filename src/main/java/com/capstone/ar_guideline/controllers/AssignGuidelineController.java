package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.AssignGuideline.AssignGuidelineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.AssignGuideline.AssignGuidelineResponse;
import com.capstone.ar_guideline.services.IAssignGuidelineService;
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
public class AssignGuidelineController {
  IAssignGuidelineService assignGuidelineService;

  @GetMapping(value = ConstAPI.AssignGuidelineAPI.ASSIGN_GUIDELINES + "/{guidelineId}")
  ApiResponse<List<AssignGuidelineResponse>> getAssignGuidelinesByGuidelineId(
      @PathVariable String guidelineId) {
    return ApiResponse.<List<AssignGuidelineResponse>>builder()
        .message("Get all assign guidelines by guideline id")
        .result(assignGuidelineService.getAssignGuidelinesByGuidelineId(guidelineId))
        .build();
  }

  @PostMapping(value = ConstAPI.AssignGuidelineAPI.ASSIGN_GUIDELINES)
  ApiResponse<AssignGuidelineResponse> createAssignGuideline(
      @RequestBody @Valid AssignGuidelineCreationRequest request) {
    return ApiResponse.<AssignGuidelineResponse>builder()
        .message("Create assign guideline")
        .result(assignGuidelineService.createAssignGuideline(request))
        .build();
  }
}
