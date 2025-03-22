package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.responses.Machine_QR.Machine_QRResponse;
import com.capstone.ar_guideline.entities.Machine_QR;

public class Machine_QRMapper {
    public static Machine_QRResponse fromEntityToMachine_QRResponse(Machine_QR machineQr) {
        return Machine_QRResponse.builder()
                .machineQrId(machineQr.getId())
                .guidelineName(machineQr.getGuideline().getTitle())
                .machineId(machineQr.getMachine().getId())
                .qrUrl(machineQr.getQrUrl())
                .build();
    }
}
