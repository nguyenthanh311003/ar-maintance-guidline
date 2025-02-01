package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.InstructionProcess.InstructionProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionProcess.InstructionProcessResponse;
import com.capstone.ar_guideline.entities.InstructionProcess;

import java.util.List;

public interface IInstructionProcessService {
    InstructionProcessResponse createInstructionProcess(InstructionProcessCreationRequest instructionProcessCreationRequest);
    List<InstructionProcessResponse> getInstructionProcessesByUserId(String userId, String CouseId);
    InstructionProcessResponse markCompleted(String instructionProcessId);
    InstructionProcess findById(String instructionProcessId);
}
