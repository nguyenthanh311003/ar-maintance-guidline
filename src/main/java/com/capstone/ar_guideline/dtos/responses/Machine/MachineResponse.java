package com.capstone.ar_guideline.dtos.responses.Machine;

import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import java.util.List;

import com.capstone.ar_guideline.dtos.responses.Machine_QR.Machine_QRResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineResponse {
  String id;
  String machineName;
  String machineType;
  String machineCode;
  String apiUrl;
  String token;
  String qrCode;
  List<MachineTypeValueResponse> machineTypeValueResponses;
  List<Machine_QRResponse> machineQrsResponses;
}
