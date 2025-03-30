package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.ICompanyRequestService;
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
public class CompanyRequestController {
  ICompanyRequestService companyRequestService;
  IARGuidelineService arGuidelineService;

  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST)
  ApiResponse<PagingModel<CompanyRequestResponse>> getAllCompanyRequestsForDesigner(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String companyName) {
    return ApiResponse.<PagingModel<CompanyRequestResponse>>builder()
        .message("Get all company requests")
        .result(companyRequestService.findAllForDesigner(page, size, status, companyName))
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST + "/{companyId}")
  ApiResponse<PagingModel<CompanyRequestResponse>> getAllCompanyRequestsByCompanyId(
      @PathVariable String companyId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String status) {
    return ApiResponse.<PagingModel<CompanyRequestResponse>>builder()
        .message("Get all company requests by Company Id")
        .result(companyRequestService.findByCompanyId(page, size, companyId, status))
        .build();
  }

  @PostMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST)
  ApiResponse<CompanyRequestResponse> createCompanyRequest(
      @RequestBody @Valid CompanyRequestCreation request) {
    return ApiResponse.<CompanyRequestResponse>builder()
        .message("Create Company Request")
        .result(companyRequestService.create(request))
        .build();
  }

  @PutMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST + "/{requestId}")
  ApiResponse<CompanyRequestResponse> updateCompanyRequest(
      @PathVariable String requestId, @RequestBody @Valid CompanyRequestCreation request) {
    return ApiResponse.<CompanyRequestResponse>builder()
        .message("Update Company Request")
        .result(companyRequestService.update(requestId, request))
        .build();
  }

  @PutMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST_UPLOAD_AGAIN + "{requestId}")
  ApiResponse<CompanyRequestResponse> uploadAgain(
      @PathVariable String requestId, @ModelAttribute @Valid ModelCreationRequest request) {
    return ApiResponse.<CompanyRequestResponse>builder()
        .message("Update Company Request")
        .result(arGuidelineService.uploadAgain(requestId, request))
        .build();
  }
}
