package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Model;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.ModelMapper;
import com.capstone.ar_guideline.repositories.ModelRepository;
import com.capstone.ar_guideline.services.IModelService;
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
public class ModelServiceImpl implements IModelService {
  ModelRepository modelRepository;
  IModelTypeService modelTypeService;
  RedisTemplate<String, Object> redisTemplate;
  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_MODEL,
    ConstHashKey.HASH_KEY_INSTRUCTION,
    ConstHashKey.HASH_KEY_INSTRUCTION_DETAIL
  };

  @Override
  public ModelResponse create(ModelCreationRequest request) {
    try {
      ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

      Model newModel = ModelMapper.fromModelCreationRequestToEntity(request, modelTypeById);
      newModel = modelRepository.save(newModel);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ModelMapper.fromEntityToModelResponse(newModel);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_CREATE_FAILED);
    }
  }

  @Override
  public ModelResponse update(String id, ModelCreationRequest request) {
    try {
      Model modelById = findById(id);
      ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

      modelById.setModelType(modelTypeById);
      modelById.setModelCode(request.getModelCode());
      modelById.setStatus(request.getStatus());
      modelById.setName(request.getName());
      modelById.setDescription(request.getDescription());
      modelById.setImage(request.getImage());
      modelById.setDocumentUrl(request.getDocumentUrl());
      modelById.setARUrl(request.getARUrl());
      modelById.setVersion(request.getVersion());
      modelById.setRotation(request.getRotation());
      modelById.setScale(request.getScale());
      modelById.setFileSize(request.getFileSize());
      modelById.setFileType(request.getFileType());

      modelById = modelRepository.save(modelById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ModelMapper.fromEntityToModelResponse(modelById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Model modelById = findById(id);
      modelRepository.deleteById(modelById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_DELETE_FAILED);
    }
  }

  @Override
  public Model findById(String id) {
    return modelRepository
        .findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.MODEL_NOT_EXISTED));
  }
}
