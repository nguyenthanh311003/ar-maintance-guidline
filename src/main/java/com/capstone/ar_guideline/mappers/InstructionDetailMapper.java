package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionDetail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InstructionDetailMapper {
  public static InstructionDetail fromInstructionDetailCreationRequestToEntity(
      InstructionDetailCreationRequest request) {
    return InstructionDetail.builder()
        .instruction(Instruction.builder().id(request.getInstructionId()).build())
        .name(request.getName())
        .orderNumber(request.getOrderNumber())
            .animationName(request.getAnimationName())
            .meshes(request.getMeshes().toString())
        .description(request.getDescription())
        .build();
  }

  public static InstructionDetailResponse fromEntityToInstructionDetailResponse(
          InstructionDetail instructionDetail) {
    List<String> meshesList = Optional.ofNullable(instructionDetail.getMeshes())
            .filter(s -> !s.isEmpty())
            .map(s -> s.replaceAll("[\\[\\]]", "").split(","))
            .map(Arrays::stream)
            .map(stream -> stream.map(String::trim).collect(Collectors.toList()))
            .orElse(Collections.emptyList());

    return InstructionDetailResponse.builder()
            .id(instructionDetail.getId())
            .name(instructionDetail.getName())
            .instructionId(instructionDetail.getInstruction().getId())
            .orderNumber(instructionDetail.getOrderNumber())
            .animationName(instructionDetail.getAnimationName())
            .meshes(meshesList)
            .description(instructionDetail.getDescription())
            .build();
  }
}
