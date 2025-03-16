package com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeAttributeCreationRequest {
    String machineTypeId;
    String attributeName;
}
