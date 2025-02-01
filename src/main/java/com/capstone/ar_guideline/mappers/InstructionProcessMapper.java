package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.InstructionProcess.InstructionProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionProcess.InstructionProcessResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionProcess;
import com.capstone.ar_guideline.entities.User;

public class InstructionProcessMapper {
    public static InstructionProcess fromCreationRequest(InstructionProcessCreationRequest request) {
        return InstructionProcess.builder()
                .instruction(Instruction.builder().id(request.getInstructionId()).build())
                .user(User.builder().id(request.getUserId()).build())
                .isDone(false)
                .build();
    }

    public static InstructionProcessResponse toResponse(InstructionProcess instructionProcess) {
        return InstructionProcessResponse.builder()
                .id(instructionProcess.getId())
                .instructionId(instructionProcess.getInstruction().getId())
                .userId(instructionProcess.getUser().getId())
                .isDone(instructionProcess.getIsDone())
                .build();
    }
}
