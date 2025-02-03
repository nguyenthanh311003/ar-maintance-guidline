package com.capstone.ar_guideline.dtos.responses.InstructionProcess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstructionProcessResponse {
  private String id;
  private String instructionId;
  private String userId;
  private Boolean isDone;
}
