package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.entities.Instruction;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInstructionService {
  Instruction create(Instruction instruction);

  InstructionResponse update(String id, InstructionCreationRequest request);

  Boolean delete(String id);

  Instruction findById(String id);

  List<InstructionResponse> findByCourseId(String modelId);

  List<Instruction> findByCourseIdReturnEntity(String courseId);

  Page<Instruction> findByCourseIdPaging(Pageable pageable, String courseId);

  Integer getHighestOrderNumber(String modelId);

  Boolean swapOrder(String instructionIdCurrent, String instructionIdSwap);

  InstructionResponse findByIdReturnResponse(String instructionId);

  Boolean deleteByCourseId(String courseId);
}
