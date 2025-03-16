package com.capstone.ar_guideline.dtos.requests.Machine;

import com.capstone.ar_guideline.dtos.requests.MachineTypeValue.MachineTypeValueCreationRequest;
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
  List<MachineTypeValueCreationRequest> machineTypeValueCreationRequest;
}
