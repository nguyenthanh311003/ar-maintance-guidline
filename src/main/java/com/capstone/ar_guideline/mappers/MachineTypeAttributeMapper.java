package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.entities.MachineTypeAttribute;
import com.capstone.ar_guideline.entities.MachineTypeValue;

public class MachineTypeAttributeMapper {
  public static MachineTypeAttribute fromMachineTypeAttributeCreationRequestToEntity(
      MachineTypeAttributeCreationRequest request) {
    return MachineTypeAttribute.builder()
        .attributeName(request.getAttributeName())
        .valueOfAttribute(request.getAttributeValue())
        .build();
  }

  public static MachineTypeAttributeResponse fromEntityToMachineTypeAttributeResponse(
      MachineTypeAttribute machineTypeAttribute) {
    return MachineTypeAttributeResponse.builder()
        .id(machineTypeAttribute.getId())
        .modelTypeId(machineTypeAttribute.getModelType().getId())
        .attributeName(machineTypeAttribute.getAttributeName())
        .valueAttribute(machineTypeAttribute.getValueOfAttribute())
        .build();
  }

  public static MachineTypeAttributeResponse fromEntityToMachineTypeAttributeResponse(
      MachineTypeAttribute machineTypeAttribute, MachineTypeValue machineTypeValue) {
    return MachineTypeAttributeResponse.builder()
        .id(machineTypeAttribute.getId())
        .modelTypeId(machineTypeAttribute.getModelType().getId())
        .attributeName(machineTypeAttribute.getAttributeName())
        .valueAttribute(machineTypeValue.getValueAttribute())
        .build();
  }
}
