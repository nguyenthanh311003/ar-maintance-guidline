package com.capstone.ar_guideline.dtos.requests.Machine;

import com.capstone.ar_guideline.dtos.requests.MachineTypeValue.MachineTypeValueModifyRequest;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineModifyRequest {
  String machineName;
  String apiUrl;
  String token;
  List<HeaderRequest> headerRequests;
  List<MachineTypeValueModifyRequest> machineTypeValueModifyRequests;
}
