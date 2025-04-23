package com.capstone.ar_guideline.dtos.responses.Machine;

import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.dtos.responses.Machine_QR.Machine_QRResponse;
import java.util.List;
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
  String machineTypeId;
  String machineCode;
  String apiUrl;
  List<HeaderResponse> headerResponses;
  String token;
  String qrCode;
  List<MachineTypeValueResponse> machineTypeValueResponses;
  List<Machine_QRResponse> machineQrsResponses;
  int qrCodesCount;
}
