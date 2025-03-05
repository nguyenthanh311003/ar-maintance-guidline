package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionDetailMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

      instructionById.setName(request.getName());
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
  public Boolean delete(String id) {
    try {
      Instruction instructionById = findById(id);
      instructionRepository.deleteById(instructionById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));
      return true;
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
  public List<InstructionResponse> findByCourseId(String courseId) {
    try {
      return instructionRepository.getByCourseId(courseId).stream()
              .map(instruction -> {
                InstructionResponse instructionResponse = InstructionMapper.fromEntityToInstructionResponse(instruction);

                List<InstructionDetailResponse> instructionDetailResponses =
                        instructionDetailRepository.getByInstructionId(instruction.getId()).stream()
                                .map(InstructionDetailMapper::fromEntityToInstructionDetailResponse)
                                .toList();
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
  public List<Instruction> findByCourseIdReturnEntity(String courseId) {
    try {
      return instructionRepository.getByCourseId(courseId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED);
    }
  }

  @Override
  public Page<Instruction> findByCourseIdPaging(Pageable pageable, String courseId) {
    try {
      return instructionRepository.getByCourseIdPaging(pageable, courseId);
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

  @Override
  public InstructionResponse findByIdReturnResponse(String instructionId) {
    try {
      Instruction instructionById = findById(instructionId);

      return InstructionMapper.fromEntityToInstructionResponse(instructionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED);
    }
  }

  @Override
  public Boolean deleteByCourseId(String courseId) {
    try {
      List<Instruction> instructionsByCourseId = instructionRepository.getByCourseId(courseId);

      if (instructionsByCourseId.isEmpty()) {
        return false;
      }

      instructionRepository.deleteAll(instructionsByCourseId);

      return true;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DELETE_FAILED);
    }
  }
}
