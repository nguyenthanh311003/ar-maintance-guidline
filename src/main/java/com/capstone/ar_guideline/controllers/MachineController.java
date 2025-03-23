package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineModifyRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MachineController {
  IARGuidelineService arGuidelineService;

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_COMPANY + "{companyId}")
  public ApiResponse<PagingModel<MachineResponse>> getMachineByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<MachineResponse>>builder()
        .result(arGuidelineService.getMachinesByCompanyId(page, size, companyId))
        .build();
  }

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_ID + "{machineId}")
  public ApiResponse<MachineResponse> getMachineById(@PathVariable String machineId) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.getMachineById(machineId))
        .build();
  }

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_CODE + "{machineCode}")
  public ApiResponse<MachineResponse> getMachineByCode(@PathVariable String machineCode) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.getMachineByCode(machineCode))
        .build();
  }

  @PostMapping(value = ConstAPI.MachineAPI.CREATE_MACHINE)
  public ApiResponse<MachineResponse> createMachine(@RequestBody MachineCreationRequest request) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.createMachine(request))
        .build();
  }

  @PutMapping(value = ConstAPI.MachineAPI.UPDATE_MACHINE + "{machineId}")
  public ApiResponse<MachineResponse> updateMachine(
      @PathVariable String machineId, @RequestBody MachineModifyRequest request) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.updateMachineById(machineId, request))
        .build();
  }
}
