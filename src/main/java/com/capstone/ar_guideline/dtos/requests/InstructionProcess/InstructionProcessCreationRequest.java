package com.capstone.ar_guideline.dtos.requests.InstructionProcess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructionProcessCreationRequest {
    private String instructionId;
    private String userId;
}
