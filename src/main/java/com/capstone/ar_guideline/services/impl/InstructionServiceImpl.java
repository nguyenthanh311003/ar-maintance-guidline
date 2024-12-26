package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.Model;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionMapper;
import com.capstone.ar_guideline.repositories.InstructionRepository;
import com.capstone.ar_guideline.services.IInstructionService;
import com.capstone.ar_guideline.services.IModelService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InstructionServiceImpl implements IInstructionService {
  InstructionRepository instructionRepository;
  RedisTemplate<String, Object> redisTemplate;
  IModelService modelService;
  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_INSTRUCTION, ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL
  };

  @Override
  public InstructionResponse create(InstructionCreationRequest request) {
    try {
      Model modelById = modelService.findById(request.getModelId());

      Instruction newInstruction =
          InstructionMapper.fromInstructionCreationRequestToEntity(request, modelById);

      newInstruction = instructionRepository.save(newInstruction);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return InstructionMapper.fromEntityToInstructionResponse(newInstruction);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_CREATE_FAILED);
    }
  }

  @Override
  public InstructionResponse update(String id, InstructionCreationRequest request) {
    try {
      Instruction instructionById = findById(id);

      Model modelById = modelService.findById(request.getModelId());

      instructionById.setModel(modelById);
      instructionById.setName(request.getName());
      instructionById.setCode(request.getCode());
      instructionById.setOrderNumber(request.getOrderNumber());
      instructionById.setDescription(request.getDescription());

      instructionById = instructionRepository.save(instructionById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return InstructionMapper.fromEntityToInstructionResponse(instructionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Instruction instructionById = findById(id);
      instructionRepository.deleteById(instructionById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DELETE_FAILED);
    }
  }

  @Override
  public Instruction findById(String id) {
    return instructionRepository
        .findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED));
  }
}
