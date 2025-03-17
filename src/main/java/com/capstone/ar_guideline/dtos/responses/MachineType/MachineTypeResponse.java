package com.capstone.ar_guideline.dtos.responses.MachineType;

import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeResponse {
  String machineTypeId;
  String machineTypeName;
  String description;
  List<MachineTypeAttributeResponse> machineTypeAttributeResponses;
  int numOfAttribute;
}
