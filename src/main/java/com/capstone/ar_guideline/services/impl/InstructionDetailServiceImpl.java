package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionDetail;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionDetailMapper;
import com.capstone.ar_guideline.repositories.InstructionDetailRepository;
import com.capstone.ar_guideline.services.IInstructionDetailService;
import com.capstone.ar_guideline.services.IInstructionService;
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
public class InstructionDetailServiceImpl implements IInstructionDetailService {
  InstructionDetailRepository instructionDetailRepository;
  RedisTemplate<String, Object> redisTemplate;
  IInstructionService instructionService;

  @Override
  public InstructionDetailResponse create(InstructionDetailCreationRequest request) {
    try {
      Instruction instructionById = instructionService.findById(request.getInstructionId());

      InstructionDetail newInstructionDetail =
          InstructionDetailMapper.fromInstructionDetailCreationRequestToEntity(
              request, instructionById);

      newInstructionDetail = instructionDetailRepository.save(newInstructionDetail);

      redisTemplate
          .opsForHash()
          .put(
              ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL,
              newInstructionDetail.getId(),
              newInstructionDetail);

      return InstructionDetailMapper.fromEntityToInstructionDetailResponse(newInstructionDetail);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_CREATE_FAILED);
    }
  }

  @Override
  public InstructionDetailResponse update(String id, InstructionDetailCreationRequest request) {
    try {
      InstructionDetail instructionDetailByIdWithRedis =
          (InstructionDetail)
              redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id);

      Instruction instructionByIdWithRedis =
          (Instruction) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_INSTRUCTION, id);

      if (!Objects.isNull(instructionDetailByIdWithRedis)
          && !Objects.isNull(instructionByIdWithRedis)) {
        instructionDetailByIdWithRedis.setInstruction(instructionByIdWithRedis);
        instructionDetailByIdWithRedis.setTriggerEvent(request.getTriggerEvent());
        instructionDetailByIdWithRedis.setOrderNumber(request.getOrderNumber());
        instructionDetailByIdWithRedis.setDescription(request.getDescription());

        instructionDetailByIdWithRedis =
            instructionDetailRepository.save(instructionDetailByIdWithRedis);

        redisTemplate
            .opsForHash()
            .put(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id, instructionDetailByIdWithRedis);

        return InstructionDetailMapper.fromEntityToInstructionDetailResponse(
            instructionDetailByIdWithRedis);
      }

      InstructionDetail instructionDetailById = findById(id);

      Instruction instructionById = instructionService.findById(request.getInstructionId());

      instructionDetailById.setInstruction(instructionById);
      instructionDetailById.setTriggerEvent(request.getTriggerEvent());
      instructionDetailById.setOrderNumber(request.getOrderNumber());
      instructionDetailById.setDescription(request.getDescription());

      instructionDetailById = instructionDetailRepository.save(instructionDetailById);

      redisTemplate
          .opsForHash()
          .put(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id, instructionDetailById);

      return InstructionDetailMapper.fromEntityToInstructionDetailResponse(instructionDetailById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      InstructionDetail instructionDetailById = findById(id);
      redisTemplate.opsForHash().delete(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id);
      instructionDetailRepository.deleteById(instructionDetailById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_DELETE_FAILED);
    }
  }

  @Override
  public InstructionDetail findById(String id) {
    try {
      InstructionDetail instructionDetailByIdWithRedis =
          (InstructionDetail)
              redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id);

      if (!Objects.isNull(instructionDetailByIdWithRedis)) {
        return instructionDetailByIdWithRedis;
      }

      InstructionDetail instructionDetailById =
          instructionDetailRepository
              .findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED));

      redisTemplate
          .opsForHash()
          .put(ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL, id, instructionDetailById);

      return instructionDetailById;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED);
    }
  }
}
