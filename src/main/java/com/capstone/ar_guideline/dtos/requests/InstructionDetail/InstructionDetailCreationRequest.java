package com.capstone.ar_guideline.dtos.requests.InstructionDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailCreationRequest {
  String instructionId;
  String triggerEvent;
  Integer orderNumber;
  String description;
  String type;
}
