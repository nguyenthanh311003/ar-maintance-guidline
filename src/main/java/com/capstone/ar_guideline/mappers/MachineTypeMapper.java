package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.MachineType.MachineTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.entities.ModelType;
import java.util.List;

public class MachineTypeMapper {
  public static ModelType fromMachineTypeCreationRequestToEntity(
      MachineTypeCreationRequest request) {
    return ModelType.builder().name(request.getMachineTypeName()).build();
  }

  public static MachineTypeResponse fromEntityToMachineTypeResponse(
      ModelType machineType, List<MachineTypeAttributeResponse> machineTypeAttributeResponses) {
    return MachineTypeResponse.builder()
        .machineTypeId(machineType.getId())
        .machineTypeName(machineType.getName())
        .machineTypeAttributeResponses(machineTypeAttributeResponses)
        .build();
  }
}
