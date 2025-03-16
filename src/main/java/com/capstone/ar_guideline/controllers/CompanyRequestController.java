package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.AssignGuideline.AssignGuidelineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.AssignGuideline.AssignGuidelineResponse;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.services.IAssignGuidelineService;
import com.capstone.ar_guideline.services.ICompanyRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CompanyRequestController {
  ICompanyRequestService companyRequestService;

  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST)
  ApiResponse<List<CompanyRequestResponse>> getAllCompanyRequests() {
    return ApiResponse.<List<CompanyRequestResponse>>builder()
            .message("Get all company requests")
            .result(companyRequestService.findAll())
            .build();
  }

//  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST + "/{companyId}")
//  ApiResponse<List<CompanyRequestResponse>> getAllCompanyRequestsByCompanyId(@PathVariable Long companyId) {
//    return ApiResponse.<List<CompanyRequestResponse>>builder()
//            .message("Get all company requests")
//            .result(companyRequestService.findByCompanyId(companyId))
//            .build();
//  }
//
//  @PostMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST)
//  ApiResponse<AssignGuidelineResponse> createAssignGuideline(
//      @RequestBody @Valid AssignGuidelineCreationRequest request) {
//    return ApiResponse.<AssignGuidelineResponse>builder()
//        .message("Create assign guideline")
//        .result(assignGuidelineService.createAssignGuideline(request))
//        .build();
//  }
}
