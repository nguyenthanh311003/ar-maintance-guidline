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
            .name(request.getName())
        .build();
  }

  public static InstructionDetailResponse fromEntityToInstructionDetailResponse(
      InstructionDetail instructionDetail) {
    return InstructionDetailResponse.builder()
        .id(instructionDetail.getId())
            .name(instructionDetail.getName())
        .instructionId(instructionDetail.getInstruction().getId())
        .orderNumber(instructionDetail.getOrderNumber()).name(instructionDetail.getName())
        .fileString(instructionDetail.getFile())
            .imgString(instructionDetail.getImgUrl())
        .build();
  }
}
