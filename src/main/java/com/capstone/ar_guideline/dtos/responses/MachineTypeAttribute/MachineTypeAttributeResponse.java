package com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineTypeAttributeResponse {
  String id;
  String modelTypeId;
  String attributeName;
  String valueAttribute;
}
