package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.ModelTypeMapper;
import com.capstone.ar_guideline.repositories.ModelTypeRepository;
import com.capstone.ar_guideline.services.IModelTypeService;
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
public class ModelTypeServiceImpl implements IModelTypeService {
  ModelTypeRepository modelTypeRepository;
  RedisTemplate<String, Object> redisTemplate;
  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_MODEL_TYPE,
    ConstHashKey.HASH_KEY_MODEL,
    ConstHashKey.HASH_KEY_INSTRUCTION,
    ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL
  };

  @Override
  public ModelTypeResponse create(ModelTypeCreationRequest request) {
    try {
      ModelType newModelType = ModelTypeMapper.fromModelTypeCreationRequestToEntity(request);
      newModelType = modelTypeRepository.save(newModelType);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ModelTypeMapper.fromEntityToModelTypeResponse(newModelType);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_CREATE_FAILED);
    }
  }

  @Override
  public ModelTypeResponse update(String id, ModelTypeCreationRequest request) {
    try {
      ModelType modelTypeById =
          ModelTypeMapper.fromModelTypeResponseToEntity(findModelTypeResponseById(id));

      modelTypeById.setName(request.getName());
      modelTypeById.setImage(request.getImage());
      modelTypeById.setDescription(request.getDescription());

      modelTypeById = modelTypeRepository.save(modelTypeById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      ModelType modelTypeById =
          ModelTypeMapper.fromModelTypeResponseToEntity(findModelTypeResponseById(id));
      modelTypeRepository.deleteById(modelTypeById.getId());

      String[] keys = {
        ConstHashKey.HASH_KEY_MODEL_TYPE,
        ConstHashKey.HASH_KEY_MODEL,
        ConstHashKey.HASH_KEY_INSTRUCTION,
        ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL
      };

      Arrays.stream(keys)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_DELETE_FAILED);
    }
  }

  @Override
  public ModelType findById(String id) {
    try {
      return modelTypeRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
    }
  }

  private ModelTypeResponse findModelTypeResponseById(String id) {
    try {
      ModelType modelTypeById =
          modelTypeRepository
              .findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED));

      return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
    }
  }
}
