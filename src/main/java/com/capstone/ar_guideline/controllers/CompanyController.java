package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponseManagement;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.ICompanyService;
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
public class CompanyController {
  ICompanyService companyService;

  @GetMapping(value = ConstAPI.CompanyAPI.GET_COMPANIES)
  ApiResponse<List<CompanyResponse>> getCompanies() {
    return ApiResponse.<List<CompanyResponse>>builder()
        .result(companyService.findAll())
        .message("Get all company")
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyAPI.GET_COMPANY_MANAGEMENT)
  ApiResponse<PagingModel<CompanyResponseManagement>> getCompaniesForManagement(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String companyName) {
    return ApiResponse.<PagingModel<CompanyResponseManagement>>builder()
        .result(companyService.findAllForManagement(page, size, companyName))
        .message("Get all company for management")
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyAPI.GET_COMPANY_BY_ID)
  ApiResponse<CompanyResponse> getCompanyById(@RequestParam String id) {
    return ApiResponse.<CompanyResponse>builder()
        .result(companyService.findById(id))
        .message("Get company by id")
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyAPI.GET_COMPANY_BY_NAME)
  ApiResponse<CompanyResponse> getCompanyByName(@RequestParam String name) {
    return ApiResponse.<CompanyResponse>builder()
        .result(companyService.findByName(name))
        .message("Get company by name")
        .build();
  }

  @GetMapping(value = ConstAPI.CompanyAPI.GET_COMPANY_BY_USER_ID)
  ApiResponse<CompanyResponse> getCompanyByUserId(@RequestParam String userId) {
    return ApiResponse.<CompanyResponse>builder()
        .result(companyService.findByUserId(userId))
        .message("Get company by name")
        .build();
  }

  /*@PostMapping(value = ConstAPI.CompanyAPI.CREATE_COMPANY)
  ApiResponse<CompanyResponse> createCompany(@RequestBody @Valid CompanyCreationRequest request) {
    return ApiResponse.<CompanyResponse>builder()
        .result(companyService.create(request))
        .message("Create company")
        .build();
  }*/
}
