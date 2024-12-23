package com.capstone.ar_guideline.dtos.responses.Instruction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionResponse {
  String id;
  String modelId;
  String code;
  Integer orderNumber;
  String name;
  String description;
}
