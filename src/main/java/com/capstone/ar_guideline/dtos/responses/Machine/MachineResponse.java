package com.capstone.ar_guideline.dtos.responses.Machine;

import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
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
  List<MachineTypeValueResponse> machineTypeValueResponses;
}
