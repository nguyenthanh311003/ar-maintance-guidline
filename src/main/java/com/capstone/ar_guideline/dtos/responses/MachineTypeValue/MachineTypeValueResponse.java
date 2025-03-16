package com.capstone.ar_guideline.dtos.responses.MachineTypeValue;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeValueResponse {
    String id;
    String machineTypeAttributeId;
    String machineTypeAttributeName;
    String valueAttribute;
}
