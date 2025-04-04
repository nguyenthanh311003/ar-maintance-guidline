package com.capstone.ar_guideline.dtos.responses.Machine_QR;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Machine_QRResponse {
  String machineQrId;
  String machineId;
  String guidelineName;
  String qrUrl;
}
