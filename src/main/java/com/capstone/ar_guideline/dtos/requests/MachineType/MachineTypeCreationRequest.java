package com.capstone.ar_guideline.dtos.requests.MachineType;

import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeCreationRequest {
  String machineName;
  String description;
  List<MachineTypeAttributeCreationRequest> machineTypeAttributeCreationRequestList;
}
