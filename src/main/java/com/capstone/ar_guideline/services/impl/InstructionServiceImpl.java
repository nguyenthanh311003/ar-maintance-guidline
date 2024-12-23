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
import java.util.Objects;
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

  @Override
  public InstructionResponse create(InstructionCreationRequest request) {
    try {
      Model modelById = modelService.findById(request.getModelId());

      Instruction newInstruction =
          InstructionMapper.fromInstructionCreationRequestToEntity(request, modelById);

      newInstruction = instructionRepository.save(newInstruction);

      redisTemplate
          .opsForHash()
          .put(ConstHashKey.HASH_KEY_INSTRUCTION, newInstruction.getId(), newInstruction);

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
      Instruction instructionByIdWithRedis =
          (Instruction) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_INSTRUCTION, id);

      Model modelByIdWithRedis =
          (Model) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_MODEL, id);

      if (!Objects.isNull(instructionByIdWithRedis) && !Objects.isNull(modelByIdWithRedis)) {
        instructionByIdWithRedis.setModel(modelByIdWithRedis);
        instructionByIdWithRedis.setName(request.getName());
        instructionByIdWithRedis.setCode(request.getCode());
        instructionByIdWithRedis.setOrderNumber(request.getOrderNumber());
        instructionByIdWithRedis.setDescription(request.getDescription());

        instructionByIdWithRedis = instructionRepository.save(instructionByIdWithRedis);

        redisTemplate
            .opsForHash()
            .put(ConstHashKey.HASH_KEY_INSTRUCTION, id, instructionByIdWithRedis);

        return InstructionMapper.fromEntityToInstructionResponse(instructionByIdWithRedis);
      }

      Instruction instructionById = findById(id);

      Model modelById = modelService.findById(request.getModelId());

      instructionById.setModel(modelById);
      instructionById.setName(request.getName());
      instructionById.setCode(request.getCode());
      instructionById.setOrderNumber(request.getOrderNumber());
      instructionById.setDescription(request.getDescription());

      instructionById = instructionRepository.save(instructionById);

      redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_INSTRUCTION, id, instructionById);

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
      redisTemplate.opsForHash().delete(ConstHashKey.HASH_KEY_INSTRUCTION, id);
      instructionRepository.deleteById(instructionById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DELETE_FAILED);
    }
  }

  @Override
  public Instruction findById(String id) {
    Instruction instructionByIdWithRedis =
        (Instruction) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_INSTRUCTION, id);

    if (!Objects.isNull(instructionByIdWithRedis)) {
      return instructionByIdWithRedis;
    }

    Instruction instructionById =
        instructionRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED));

    redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_INSTRUCTION, id, instructionById);
    return instructionById;
  }
}
