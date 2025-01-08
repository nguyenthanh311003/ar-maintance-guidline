package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionDetail;

public class InstructionDetailMapper {
  public static InstructionDetail fromInstructionDetailCreationRequestToEntity(
      InstructionDetailCreationRequest request, Instruction instruction) {
    return InstructionDetail.builder()
        .instruction(instruction)
        .triggerEvent(request.getTriggerEvent())
        .orderNumber(request.getOrderNumber())
        .description(request.getDescription())
        .type(request.getType())
        .build();
  }

  public static InstructionDetailResponse fromEntityToInstructionDetailResponse(
      InstructionDetail instructionDetail) {
    return InstructionDetailResponse.builder()
        .id(instructionDetail.getId())
        .instructionId(instructionDetail.getInstruction().getId())
        .triggerEvent(instructionDetail.getTriggerEvent())
        .orderNumber(instructionDetail.getOrderNumber())
        .description(instructionDetail.getDescription())
        .type(instructionDetail.getType())
        .build();
  }
}
