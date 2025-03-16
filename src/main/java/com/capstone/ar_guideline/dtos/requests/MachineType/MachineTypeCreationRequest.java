package com.capstone.ar_guideline.dtos.requests.MachineType;

import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
