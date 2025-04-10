package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.PointRequestResponse.PointRequestCreation;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.PointRequestResponse.PointRequestResponse;
import com.capstone.ar_guideline.services.IPointRequestService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PointRequestController {
  private final IPointRequestService pointRequestService;

  @GetMapping(value = ConstAPI.PointRequestAPI.POINT_REQUEST_ENDPOINT)
  ApiResponse<List<PointRequestResponse>> getAllPointRequests() {
    return ApiResponse.<List<PointRequestResponse>>builder()
        .message("Get all point requests")
        .result(pointRequestService.findAll())
        .build();
  }

  @GetMapping(value = ConstAPI.PointRequestAPI.POINT_REQUEST_ENDPOINT + "/company/{companyId}")
  ApiResponse<PagingModel<PointRequestResponse>> getAllPointRequestsByCompany(
      @PathVariable String companyId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String requestNumber,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String employeeEmail,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate createDate) {
    return ApiResponse.<PagingModel<PointRequestResponse>>builder()
        .message("Get all point requests by Company Id")
        .result(
            pointRequestService.findAllByCompanyId(
                page, size, companyId, requestNumber, status, employeeEmail, createDate))
        .build();
  }

  @GetMapping(value = ConstAPI.PointRequestAPI.POINT_REQUEST_ENDPOINT + "/employee/{employeeId}")
  ApiResponse<List<PointRequestResponse>> getAllPointRequestsByEmployee(
      @PathVariable String employeeId) {
    return ApiResponse.<List<PointRequestResponse>>builder()
        .message("Get all point requests by Employee Id")
        .result(pointRequestService.findAllByEmployeeId(employeeId))
        .build();
  }

  @PostMapping(value = ConstAPI.PointRequestAPI.POINT_REQUEST_ENDPOINT)
  ApiResponse<PointRequestResponse> createPointRequest(@RequestBody PointRequestCreation request) {
    return ApiResponse.<PointRequestResponse>builder()
        .message("Create Point Request")
        .result(pointRequestService.create(request))
        .build();
  }

  @PutMapping(value = ConstAPI.PointRequestAPI.POINT_REQUEST_ENDPOINT + "/{requestId}")
  ApiResponse<PointRequestResponse> updatePointRequest(
      @PathVariable String requestId, @RequestBody PointRequestCreation request) {
    return ApiResponse.<PointRequestResponse>builder()
        .message("Update Point Request")
        .result(pointRequestService.update(requestId, request))
        .build();
  }
}
