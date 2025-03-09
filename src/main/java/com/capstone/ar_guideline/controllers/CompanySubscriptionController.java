package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
import com.capstone.ar_guideline.services.ICompanySubscriptionService;
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
public class CompanySubscriptionController {
  ICompanySubscriptionService companySubscriptionService;

  @PostMapping(value = ConstAPI.CompanySubscriptionAPI.CREATE_COMPANY_SUBSCRIPTION)
  ApiResponse<CompanySubscriptionResponse> createCompanySubscription(
      @RequestBody @Valid ComSubscriptionCreationRequest request) {
    return ApiResponse.<CompanySubscriptionResponse>builder()
        .result(companySubscriptionService.create(request))
        .build();
  }

  @PutMapping(
      value =
          ConstAPI.CompanySubscriptionAPI.UPDATE_COMPANY_SUBSCRIPTION + "{companySubscriptionId}")
  ApiResponse<CompanySubscriptionResponse> updateCompanySubscription(
      @PathVariable String companySubscriptionId,
      @RequestBody ComSubscriptionCreationRequest request) {
    return ApiResponse.<CompanySubscriptionResponse>builder()
        .result(companySubscriptionService.update(companySubscriptionId, request))
        .build();
  }

  @DeleteMapping(
      value =
          ConstAPI.CompanySubscriptionAPI.DELETE_COMPANY_SUBSCRIPTION + "{companySubscriptionId}")
  ApiResponse<String> deleteCompanySubscription(@PathVariable String companySubscriptionId) {
    companySubscriptionService.delete(companySubscriptionId);
    return ApiResponse.<String>builder()
        .result("Company Subscription detail has been deleted")
        .build();
  }

  @GetMapping(value = ConstAPI.CompanySubscriptionAPI.FIND_BY_COMPANY_ID + "{companyId}")
  ApiResponse<CompanySubscriptionResponse> findByCompanyId(@PathVariable String companyId) {
    return ApiResponse.<CompanySubscriptionResponse>builder()
        .result(companySubscriptionService.findResponseById(companyId))
        .build();
  }
}
