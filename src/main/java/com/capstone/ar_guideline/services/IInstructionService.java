package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.entities.Instruction;

public interface IInstructionService {
  InstructionResponse create(InstructionCreationRequest request);

  InstructionResponse update(String id, InstructionCreationRequest request);

  void delete(String id);

  Instruction findById(String id);
}
