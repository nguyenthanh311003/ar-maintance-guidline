package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.entities.MachineTypeAttribute;

public class MachineTypeAttributeMapper {
  public static MachineTypeAttribute fromMachineTypeAttributeCreationRequestToEntity(
      MachineTypeAttributeCreationRequest request) {
    return MachineTypeAttribute.builder().attributeName(request.getAttributeName()).build();
  }

  public static MachineTypeAttributeResponse fromEntityToMachineTypeAttributeResponse(
      MachineTypeAttribute machineTypeAttribute) {
    return MachineTypeAttributeResponse.builder()
        .id(machineTypeAttribute.getId())
        .modelTypeId(machineTypeAttribute.getModelType().getId())
        .attributeName(machineTypeAttribute.getAttributeName())
        .build();
  }
}
