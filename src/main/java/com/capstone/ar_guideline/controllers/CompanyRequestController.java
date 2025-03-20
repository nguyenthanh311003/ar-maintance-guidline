package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.services.ICompanyRequestService;
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
public class CompanyRequestController {
  ICompanyRequestService companyRequestService;

  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST)
  ApiResponse<List<CompanyRequestResponse>> getAllCompanyRequests() {
    return ApiResponse.<List<CompanyRequestResponse>>builder()
        .message("Get all company requests")
        .result(companyRequestService.findAll())
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyRequestAPI.COMPANY_REQUEST + "/{companyId}")
  ApiResponse<List<CompanyRequestResponse>> getAllCompanyRequestsByCompanyId(
      @PathVariable String companyId) {
    return ApiResponse.<List<CompanyRequestResponse>>builder()
        .message("Get all company requests by Company Id")
        .result(companyRequestService.findByCompanyId(companyId))
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
}
