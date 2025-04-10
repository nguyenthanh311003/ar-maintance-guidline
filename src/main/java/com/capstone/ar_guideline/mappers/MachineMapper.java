package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineGuidelineResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.entities.Machine;
import java.util.List;

public class MachineMapper {
  public static Machine fromMachineCreationRequestToEntity(MachineCreationRequest request) {
    return Machine.builder()
        .name(request.getMachineName())
        .apiUrl(request.getApiUrl())
        .requestToken(request.getToken())
        .build();
  }

  public static MachineResponse fromEntityToMachineResponse(Machine machine) {
    return MachineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .machineCode(machine.getMachineCode())
        .qrCode(machine.getQrCode())
        .build();
  }

  public static MachineGuidelineResponse fromEntityToMachineGuidelineResponse(Machine machine) {
    return MachineGuidelineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .machineCode(machine.getMachineCode())
        .build();
  }

  public static MachineResponse fromEntityToMachineResponseForCreate(
      Machine machine, List<MachineTypeValueResponse> machineTypeValueResponses) {
    return MachineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .machineCode(machine.getMachineCode())
        .apiUrl(machine.getApiUrl())
        .token(machine.getRequestToken())
        .qrCode(machine.getQrCode())
        .machineTypeValueResponses(machineTypeValueResponses)
        .build();
  }

  public static MachineResponse fromEntityToMachineResponseForCreate(Machine machine) {
    return MachineResponse.builder()
        .id(machine.getId())
        .machineName(machine.getName())
        .machineType(machine.getModelType().getName())
        .apiUrl(machine.getApiUrl())
        .token(machine.getRequestToken())
        .qrCode(machine.getQrCode())
        .build();
  }
}
