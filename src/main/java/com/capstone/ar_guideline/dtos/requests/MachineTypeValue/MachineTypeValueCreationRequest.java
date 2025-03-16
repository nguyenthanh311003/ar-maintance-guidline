package com.capstone.ar_guideline.dtos.requests.MachineTypeValue;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeValueCreationRequest {
    String machineTypeAttributeId;
    String valueAttribute;
}
