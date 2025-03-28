package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.MachineType.MachineTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IMachineTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MachineTypeController {
  IARGuidelineService arGuidelineService;
  IMachineTypeService machineTypeService;

  @GetMapping(value = ConstAPI.MachineTypeAPI.GET_MACHINE_TYPES_BY_COMPANY_ID + "{companyId}")
  public ApiResponse<PagingModel<MachineTypeResponse>> getMachineTypeByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String name,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<MachineTypeResponse>>builder()
        .result(arGuidelineService.getMachineTypesByCompanyId(page, size, companyId, name))
        .build();
  }

  @GetMapping(value = ConstAPI.MachineTypeAPI.GET_MACHINE_TYPES_BY_ID + "{machineTypeId}")
  public ApiResponse<MachineTypeResponse> getMachineTypeById(@PathVariable String machineTypeId) {
    return ApiResponse.<MachineTypeResponse>builder()
        .result(arGuidelineService.getMachineTypesById(machineTypeId))
        .build();
  }

  @PostMapping(value = ConstAPI.MachineTypeAPI.CREATE_MACHINE_TYPE)
  public ApiResponse<MachineTypeResponse> createMachineType(
      @RequestBody MachineTypeCreationRequest request) {
    return ApiResponse.<MachineTypeResponse>builder()
        .result(arGuidelineService.createMachineType(request))
        .build();
  }

  @PutMapping(value = ConstAPI.MachineTypeAPI.UPDATE_MACHINE_TYPE + "{machineTypeId}")
  public ApiResponse<MachineTypeResponse> updateMachineType(
      @PathVariable String machineTypeId, @RequestBody MachineTypeCreationRequest request) {
    return ApiResponse.<MachineTypeResponse>builder()
        .result(arGuidelineService.updateMachineType(machineTypeId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.MachineTypeAPI.DELETE_MACHINE_TYPES + "{machineTypeId}")
  public ApiResponse<String> deleteMachineType(@PathVariable String machineTypeId) {
    machineTypeService.delete(machineTypeId);
    return ApiResponse.<String>builder()
        .result("Machine Type has been deleted successfully")
        .build();
  }
}
