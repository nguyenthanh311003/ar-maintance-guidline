package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.entities.Machine;
import java.util.List;

public class MachineMapper {
  public static Machine fromMachineCreationRequestToEntity(MachineCreationRequest request) {
    return Machine.builder().name(request.getMachineName()).build();
  }

  public static MachineResponse fromEntityToMachineResponse(Machine machine) {
    return MachineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .build();
  }

  public static MachineResponse fromEntityToMachineResponseForCreate(
      Machine machine, List<MachineTypeValueResponse> machineTypeValueResponses) {
    return MachineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .machineTypeValueResponses(machineTypeValueResponses)
        .build();
  }
}
