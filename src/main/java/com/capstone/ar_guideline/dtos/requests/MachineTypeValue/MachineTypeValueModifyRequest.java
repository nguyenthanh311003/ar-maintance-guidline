package com.capstone.ar_guideline.dtos.requests.MachineTypeValue;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeValueModifyRequest {
    String machineTypeValueId;
    String machineTypeAttributeId;
    String machineTypeAttributeName;
    String valueAttribute;
}
