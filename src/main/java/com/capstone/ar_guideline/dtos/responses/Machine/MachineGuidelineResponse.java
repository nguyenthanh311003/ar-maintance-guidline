package com.capstone.ar_guideline.dtos.responses.Machine;

import com.capstone.ar_guideline.dtos.responses.Machine_QR.Machine_QRResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineGuidelineResponse {
  String id;
  String machineName;
  String machineType;
  String machineCode;
  Machine_QRResponse machineQrsResponse;
}
