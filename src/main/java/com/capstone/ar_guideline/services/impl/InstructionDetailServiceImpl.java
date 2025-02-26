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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.capstone.ar_guideline.util.UtilService;
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
    public InstructionDetailResponse create(
            InstructionDetailCreationRequest request, String instructionId) {
        try {
            Instruction instructionById;
            if (instructionId.isEmpty()) {
                instructionById = instructionService.findById(request.getInstructionId());
            } else {
                instructionById = instructionService.findById(instructionId);
            }

            InstructionDetail newInstructionDetail =
                    InstructionDetailMapper.fromInstructionDetailCreationRequestToEntity(
                            request, instructionById);
            newInstructionDetail.setFile(FileStorageService.storeFile(request.getFile()));
            newInstructionDetail.setImgUrl(FileStorageService.storeFile(request.getImageFile()));
            Integer highestOrderNumber = getHighestOrderNumber(instructionById.getId());

            if (Objects.isNull(highestOrderNumber)) {
                newInstructionDetail.setOrderNumber(1);
            } else {
                newInstructionDetail.setOrderNumber(highestOrderNumber + 1);
            }

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

            if (request.getFile() != null) {
                instructionDetailById.setFile(FileStorageService.storeFile(request.getFile()));
            }
            if (request.getImageFile() != null) {
                instructionDetailById.setImgUrl(FileStorageService.storeFile(request.getImageFile()));
            }

            instructionDetailById.setName(request.getName());
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

            Arrays.stream(keysToRemove)
                    .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
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

            return instructionDetails.stream().map(
                    InstructionDetailMapper::fromEntityToInstructionDetailResponse
            ).toList();
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
