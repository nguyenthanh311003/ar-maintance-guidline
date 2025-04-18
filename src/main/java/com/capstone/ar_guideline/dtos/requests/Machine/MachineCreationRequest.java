package com.capstone.ar_guideline.dtos.requests.Machine;

import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineCreationRequest {
  String machineName;
  String modelTypeId;
  String companyId;
  String apiUrl;
  String machineCode;
  String token;
  //  List<MachineTypeValueCreationRequest> machineTypeValueCreationRequest;
  List<HeaderRequest> headerRequests;
}
