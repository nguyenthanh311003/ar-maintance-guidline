package com.capstone.ar_guideline.dtos.requests.Instruction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionCreationRequest {
  String courseId;
  Integer orderNumber;
  String name;
  String description;
}
