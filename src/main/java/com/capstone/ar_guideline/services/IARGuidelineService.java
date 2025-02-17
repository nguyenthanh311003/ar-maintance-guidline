package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;

public interface IARGuidelineService {
  InstructionResponse createInstruction(InstructionCreationRequest request);

  ModelResponse findModelById(String modelId);
}
