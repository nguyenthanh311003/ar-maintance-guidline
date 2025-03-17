package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IMachineTypeAttributeService;
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
public class MachineTypeAttributeController {
  IMachineTypeAttributeService machineTypeAttributeService;
  IARGuidelineService arGuidelineService;

  @GetMapping(
      value =
          ConstAPI.MachineTypeAttributeAPI.GET_MACHINES_TYPE_ATTRIBUTE_BY_MACHINE_TYPE
              + "{machineTypeId}")
  public ApiResponse<List<MachineTypeAttributeResponse>> getMachineTypeAttributeByMachineTypeId(
      @PathVariable String machineTypeId) {
    return ApiResponse.<List<MachineTypeAttributeResponse>>builder()
        .result(machineTypeAttributeService.getMachineTypeAttributeByMachineTypeId(machineTypeId))
        .build();
  }

  @PostMapping(value = ConstAPI.MachineTypeAttributeAPI.CREATE_MACHINE_TYPE_ATTRIBUTE)
  public ApiResponse<MachineTypeAttributeResponse> createMachineTypeAttribute(
      @RequestBody MachineTypeAttributeCreationRequest request) {
    return ApiResponse.<MachineTypeAttributeResponse>builder()
        .result(arGuidelineService.createMachineTypeAttribute(request))
        .build();
  }

  @DeleteMapping(
      value =
          ConstAPI.MachineTypeAttributeAPI.DELETE_MACHINE_TYPE_ATTRIBUTE
              + "{machineTypeAttributeId}")
  public ApiResponse<String> deleteCMachineTypeAttribute(
      @PathVariable String machineTypeAttributeId) {
    machineTypeAttributeService.delete(machineTypeAttributeId);
    return ApiResponse.<String>builder()
        .result("Machine Type Attribute has been deleted successfully")
        .build();
  }
}
