package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.MachineTypeValue.MachineTypeValueCreationRequest;
import com.capstone.ar_guideline.dtos.requests.MachineTypeValue.MachineTypeValueModifyRequest;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.entities.Machine;
import com.capstone.ar_guideline.entities.MachineTypeValue;

public class MachineTypeValueMapper {
    public static MachineTypeValue fromMachineTypeValueCreationRequestToEntity(MachineTypeValueCreationRequest request) {
        return MachineTypeValue.builder()
                .valueAttribute(request.getValueAttribute())
                .build();
    }

    public static MachineTypeValue fromMachineTypeValueModifyRequestToEntity(MachineTypeValueModifyRequest request) {
        return MachineTypeValue.builder()
                .valueAttribute(request.getValueAttribute())
                .build();
    }

    public static MachineTypeValueResponse fromEntityToMachineTypeValueResponse(MachineTypeValue machineTypeValue) {
        return MachineTypeValueResponse.builder()
                .id(machineTypeValue.getId())
                .valueAttribute(machineTypeValue.getValueAttribute())
                .machineTypeAttributeId(machineTypeValue.getMachineTypeAttribute().getId())
                .machineTypeAttributeName(machineTypeValue.getMachineTypeAttribute().getAttributeName())
                .build();
    }
}
