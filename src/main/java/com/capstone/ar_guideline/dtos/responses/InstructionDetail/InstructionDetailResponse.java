package com.capstone.ar_guideline.dtos.responses.InstructionDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailResponse {
  String id;
  String instructionId;
  Integer orderNumber;
  String description;
  String fileString;
}
