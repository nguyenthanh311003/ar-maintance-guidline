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
public class InstructionDetailServiceImpl implements IInstructionDetailService {
  InstructionDetailRepository instructionDetailRepository;
  RedisTemplate<String, Object> redisTemplate;
  IInstructionService instructionService;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL};

  @Override
  public InstructionDetailResponse create(InstructionDetailCreationRequest request) {
    try {
      Instruction instructionById = instructionService.findById(request.getInstructionId());

      InstructionDetail newInstructionDetail =
          InstructionDetailMapper.fromInstructionDetailCreationRequestToEntity(
              request, instructionById);

      newInstructionDetail = instructionDetailRepository.save(newInstructionDetail);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

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

      InstructionDetail instructionDetailById = findById(id);

      Instruction instructionById = instructionService.findById(request.getInstructionId());

      instructionDetailById.setInstruction(instructionById);
      instructionDetailById.setTriggerEvent(request.getTriggerEvent());
      instructionDetailById.setOrderNumber(request.getOrderNumber());
      instructionDetailById.setDescription(request.getDescription());

      instructionDetailById = instructionDetailRepository.save(instructionDetailById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

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
      instructionDetailRepository.deleteById(instructionDetailById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

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
      return instructionDetailRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED);
    }
  }
}
