package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.Model;

public class InstructionMapper {
  public static Instruction fromInstructionCreationRequestToEntity(
      InstructionCreationRequest request, Model model) {
    return Instruction.builder()
        .model(model)
        .code(request.getCode())
        .orderNumber(request.getOrderNumber())
        .name(request.getName())
        .description(request.getDescription())
        .build();
  }

  public static InstructionResponse fromEntityToInstructionResponse(Instruction instruction) {
    return InstructionResponse.builder()
        .id(instruction.getId())
        .modelId(instruction.getModel().getId())
        .code(instruction.getCode())
        .orderNumber(instruction.getOrderNumber())
        .name(instruction.getName())
        .description(instruction.getDescription())
        .build();
  }
}
