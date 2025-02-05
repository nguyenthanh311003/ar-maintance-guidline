package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.InstructionProcess.InstructionProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionProcess.InstructionProcessResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionProcess;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionProcessMapper;
import com.capstone.ar_guideline.repositories.InstructionProcessRepository;
import com.capstone.ar_guideline.services.IInstructionProcessService;
import com.capstone.ar_guideline.services.IInstructionService;
import com.capstone.ar_guideline.services.IUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructionProcessService implements IInstructionProcessService {

  @Autowired private InstructionProcessRepository instructionProcessRepository;

  @Autowired private IInstructionService instructionService;

  @Autowired private IUserService userService;

  @Override
  public InstructionProcessResponse createInstructionProcess(
      InstructionProcessCreationRequest instructionProcessCreationRequest) {

    Instruction instruction =
        instructionService.findById(instructionProcessCreationRequest.getInstructionId());
    User user = userService.findById(instructionProcessCreationRequest.getUserId());

    InstructionProcess instructionProcess =
        InstructionProcessMapper.fromCreationRequest(instructionProcessCreationRequest);

    instructionProcessRepository.save(instructionProcess);

    return InstructionProcessMapper.toResponse(instructionProcess);
  }

  @Override
  public List<InstructionProcessResponse> getInstructionProcessesByUserId(
      String userId, String courseId) {
    List<InstructionProcess> instructionProcesses =
        instructionProcessRepository.findByUserIdAndInstruction(userId, courseId);
    List<InstructionProcessResponse> instructionProcessResponses = new ArrayList<>();
    for (InstructionProcess instructionProcess : instructionProcesses) {
      InstructionProcessResponse instructionProcessResponse =
          InstructionProcessMapper.toResponse(instructionProcess);
      instructionProcessResponses.add(instructionProcessResponse);
    }
    return instructionProcessResponses;
  }

  @Override
  public InstructionProcessResponse markCompleted(String instructionProcessId) {
    InstructionProcess instructionProcess = findById(instructionProcessId);
    instructionProcess.setIsDone(true);
    return InstructionProcessMapper.toResponse(instructionProcess);
  }

  @Override
  public InstructionProcess findById(String instructionProcessId) {

    return instructionProcessRepository
        .findById(instructionProcessId)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_PROCESS_NOT_EXISTED));
  }
}
