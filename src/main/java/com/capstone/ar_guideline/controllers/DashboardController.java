package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Dashboard.AdminDashboardResponse;
import com.capstone.ar_guideline.dtos.responses.Dashboard.CompanyDashboardResponse;
import com.capstone.ar_guideline.services.impl.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DashboardController {

  @Autowired DashboardService dashboardService;

  @GetMapping(value = ConstAPI.DashboardAPI.ADMIN_DASHBOARD)
  ApiResponse<AdminDashboardResponse> findAdminDashboard() {
    return ApiResponse.<AdminDashboardResponse>builder()
        .result(dashboardService.getAdminDashboard())
        .build();
  }

  @GetMapping(value = ConstAPI.DashboardAPI.COMPANY_DASHBOARD + "/{companyId}")
  ApiResponse<CompanyDashboardResponse> findCompanyDashboard(@PathVariable String companyId) {
    return ApiResponse.<CompanyDashboardResponse>builder()
        .result(dashboardService.getCompanyDashboardById(companyId))
        .build();
  }
}
