package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionMapper;
import com.capstone.ar_guideline.repositories.InstructionDetailRepository;
import com.capstone.ar_guideline.repositories.InstructionRepository;
import com.capstone.ar_guideline.services.IInstructionService;
import com.capstone.ar_guideline.services.IModelService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
  InstructionDetailRepository instructionDetailRepository;
  RedisTemplate<String, Object> redisTemplate;
  IModelService modelService;
  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_INSTRUCTION, ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL
  };

  @Override
  public Instruction create(Instruction instruction) {
    try {
      return instructionRepository.save(instruction);
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

      List<Float> translations = request.getGuideViewPosition().getTranslation();
      List<Float> rotations = request.getGuideViewPosition().getRotation();

      instructionById.setName(request.getName());
      instructionById.setDescription(request.getDescription());

      String position =
          translations.stream().map(String::valueOf).collect(Collectors.joining(", "));
      String rotation = rotations.stream().map(String::valueOf).collect(Collectors.joining(", "));
      instructionById.setPosition(position);
      instructionById.setRotation(rotation);

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

  @Override
  public List<InstructionResponse> findByCourseId(String modelId) {
    try {
      return instructionRepository.getByCourseId(modelId).stream()
          .map(
              i -> {
                InstructionResponse instructionResponse = new InstructionResponse();
                List<InstructionDetailResponse> instructionDetailResponses =
                    instructionDetailRepository.getByInstructionId(i.getId()).stream()
                        .map(
                            ide ->
                                InstructionDetailResponse.builder()
                                    .id(ide.getId())
                                    .instructionId(ide.getInstruction().getId())
                                    .orderNumber(ide.getOrderNumber())
                                    .description(ide.getDescription())
                                        .imgString(ide.getImgUrl())
                                        .name(ide.getName())
                                    .fileString(ide.getFile())
                                    .build())
                        .toList();
                instructionResponse.setId(i.getId());
                instructionResponse.setOrderNumber(i.getOrderNumber());
                instructionResponse.setName(i.getName());
                instructionResponse.setDescription(i.getDescription());
                instructionResponse.setPosition(i.getPosition());
                instructionResponse.setRotation(i.getRotation());
                instructionResponse.setInstructionDetailResponse(instructionDetailResponses);

                return instructionResponse;
              })
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED);
    }
  }

  @Override
  public Integer getHighestOrderNumber(String modelId) {
    try {
      return instructionRepository.getHighestOrderNumber(modelId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.GET_HIGHEST_ORDER_FAIL);
    }
  }

  @Override
  public Boolean swapOrder(String instructionIdCurrent, String instructionIdSwap) {
    try {
      Instruction instructionCurrent =
          instructionRepository
              .findById(instructionIdCurrent)
              .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED));

      Instruction instructionSwap =
          instructionRepository
              .findById(instructionIdSwap)
              .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED));

      Integer orderNumberCurrent = instructionCurrent.getOrderNumber();
      Integer orderNumberSwap = instructionSwap.getOrderNumber();
      instructionCurrent.setOrderNumber(orderNumberSwap);
      instructionSwap.setOrderNumber(orderNumberCurrent);

      instructionCurrent = instructionRepository.save(instructionCurrent);

      if (instructionCurrent.getId() == null) {
        throw new AppException(ErrorCode.INSTRUCTION_UPDATE_FAILED);
      }

      instructionSwap = instructionRepository.save(instructionSwap);

      if (instructionSwap.getId() == null) {
        throw new AppException(ErrorCode.INSTRUCTION_UPDATE_FAILED);
      }

      return true;

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.SWAP_ORDER_NUMBER_FAILED);
    }
  }
}
