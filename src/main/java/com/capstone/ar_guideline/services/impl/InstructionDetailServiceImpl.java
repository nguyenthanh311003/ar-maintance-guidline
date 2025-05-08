package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionDetail;
import com.capstone.ar_guideline.entities.ServicePrice;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionDetailMapper;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.repositories.InstructionDetailRepository;
import com.capstone.ar_guideline.repositories.ServicePricerRepository;
import com.capstone.ar_guideline.services.IInstructionDetailService;
import com.capstone.ar_guideline.services.IInstructionService;
import java.util.List;
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
  CourseRepository courseRepository;
  ServicePricerRepository servicePricerRepository;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL};

  @Override
  public InstructionDetailResponse create(InstructionDetailCreationRequest request) {
    try {

InstructionDetail instructionDetailById = instructionDetailRepository.findByNameAndInstructionId(
          request.getName(), request.getInstructionId());

        if (instructionDetailById != null) {
            throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NAME_EXISTED);
        }

      Instruction instructionById = instructionService.findById(request.getInstructionId());
      Course course = instructionById.getCourse();
      InstructionDetail newInstructionDetail =
          InstructionDetailMapper.fromInstructionDetailCreationRequestToEntity(request);
      Integer highestOrderNumber = getHighestOrderNumber(instructionById.getId());

      if (Objects.isNull(highestOrderNumber)) {
        newInstructionDetail.setOrderNumber(1);
      } else {
        newInstructionDetail.setOrderNumber(highestOrderNumber + 1);
      }
      course.setStatus(ConstStatus.INACTIVE_STATUS);
      courseRepository.save(course);
      newInstructionDetail.setStatus(ConstStatus.DRAFTED);

      newInstructionDetail = instructionDetailRepository.save(newInstructionDetail);

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

      return InstructionDetailMapper.fromEntityToInstructionDetailResponse(
          instructionDetailRepository.save(
              InstructionDetailMapper.fromInstructionDetailCreationRequestToEntityToUpdate(
                  instructionDetailById, request)));
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
      Course course = instructionDetailById.getInstruction().getCourse();
      course.setStatus(ConstStatus.INACTIVE_STATUS);
        courseRepository.save(course);
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

  @Override
  public InstructionDetailResponse findByIdReturnResponse(String id) {
    try {
      InstructionDetail instructionDetailById = findById(id);
      return InstructionDetailMapper.fromEntityToInstructionDetailResponse(instructionDetailById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED);
    }
  }

  @Override
  public List<InstructionDetail> findByInstructionId(String instructionId) {
    try {
      return instructionDetailRepository.getByInstructionId(instructionId);

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED);
    }
  }

  @Override
  public List<InstructionDetailResponse> findByInstructionIdReturnResponse(String instructionId) {
    try {
      List<InstructionDetail> instructionDetails =
          instructionDetailRepository.getByInstructionId(instructionId);

      return instructionDetails.stream()
          .map(InstructionDetailMapper::fromEntityToInstructionDetailResponse)
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED);
    }
  }

  @Override
  public Boolean swapOrder(String instructionDetailIdCurrent, String instructionDetailIdSwap) {
    try {
      InstructionDetail instructionDetailCurrent =
          instructionDetailRepository
              .findById(instructionDetailIdCurrent)
              .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED));

      InstructionDetail instructionDetailSwap =
          instructionDetailRepository
              .findById(instructionDetailIdSwap)
              .orElseThrow(() -> new AppException(ErrorCode.INSTRUCTION_DETAIL_NOT_EXISTED));

      Integer orderNumberCurrent = instructionDetailCurrent.getOrderNumber();
      Integer orderNumberSwap = instructionDetailSwap.getOrderNumber();

      instructionDetailCurrent.setOrderNumber(orderNumberSwap);
      instructionDetailSwap.setOrderNumber(orderNumberCurrent);

      instructionDetailCurrent = instructionDetailRepository.save(instructionDetailCurrent);

      if (instructionDetailCurrent.getId() == null) {
        throw new AppException(ErrorCode.INSTRUCTION_DETAIL_UPDATE_FAILED);
      }

      instructionDetailSwap = instructionDetailRepository.save(instructionDetailSwap);

      if (instructionDetailSwap.getId() == null) {
        throw new AppException(ErrorCode.INSTRUCTION_DETAIL_UPDATE_FAILED);
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
  public Boolean deleteByInstructionId(String instructionId) {
    try {
      List<InstructionDetail> details =
          instructionDetailRepository.getByInstructionId(instructionId);

      if (details.isEmpty()) {
        return false;
      }

      instructionDetailRepository.deleteAll(details);

      return true;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_DELETE_FAILED);
    }
  }

  @Override
  public Long countInstructionDetailByCourseId(String courseId) {
    try {
      ServicePrice servicePrice = servicePricerRepository.findByName("Create Guideline");
      return instructionDetailRepository.countInstructionDetailByCourseId(courseId).size()
          * servicePrice.getPrice();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_DETAIL_COUNT_FAILED);
    }
  }

  private Integer getHighestOrderNumber(String instructionId) {
    try {
      return instructionDetailRepository.getHighestOrderNumber(instructionId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.GET_HIGHEST_ORDER_FAIL);
    }
  }
}
