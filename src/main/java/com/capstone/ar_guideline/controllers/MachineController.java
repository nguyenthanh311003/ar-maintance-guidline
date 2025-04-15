package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineModifyRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineGuidelineResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IMachineService;
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
public class MachineController {
  IARGuidelineService arGuidelineService;
  IMachineService machineService;

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_COMPANY + "{companyId}")
  public ApiResponse<PagingModel<MachineResponse>> getMachineByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String machineTypeName,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<MachineResponse>>builder()
        .result(
            arGuidelineService.getMachinesByCompanyId(
                page, size, companyId, keyword, machineTypeName))
        .build();
  }

  @DeleteMapping(value = ConstAPI.MachineAPI.DELETE_MACHINE_BY_ID + "{machineId}")
  public ApiResponse<String> deleteMachine(@PathVariable String machineId) {
    machineService.delete(machineId);
    return ApiResponse.<String>builder().result("Machine has been deleted successfully").build();
  }

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_ID + "{machineId}")
  public ApiResponse<MachineResponse> getMachineById(@PathVariable String machineId) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.getMachineById(machineId))
        .build();
  }

  @GetMapping(
      value = ConstAPI.MachineAPI.GET_MACHINES_BY_CODE + "{machineCode}" + "/company/{companyId}")
  public ApiResponse<MachineResponse> getMachineByCode(
      @PathVariable String machineCode, @PathVariable String companyId) {
    return ApiResponse.<MachineResponse>builder()
        .result(arGuidelineService.getMachineByCode(machineCode, companyId))
        .build();
  }

  @GetMapping(value = ConstAPI.MachineAPI.GET_MACHINES_BY_GUIDELINE_ID + "{guidelineId}")
  public ApiResponse<List<MachineResponse>> getMachineByGuidelineId(
      @PathVariable String guidelineId) {
    return ApiResponse.<List<MachineResponse>>builder()
        .result(arGuidelineService.getMachineByGuidelineId(guidelineId))
        .build();
  }

  @GetMapping(
      value = ConstAPI.MachineAPI.GET_MACHINES_BY_GUIDELINE_ID_AND_MACHINE_ID + "{guidelineId}")
  public ApiResponse<List<MachineGuidelineResponse>> getMachineByGuidelineIdAndMachineId(
      @PathVariable String guidelineId) {
    return ApiResponse.<List<MachineGuidelineResponse>>builder()
        .result(arGuidelineService.getMachineForMachineTabByGuidelineId(guidelineId))
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
