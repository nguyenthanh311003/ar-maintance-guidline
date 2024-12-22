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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelTypeServiceImpl implements IModelTypeService {
    ModelTypeRepository modelTypeRepository;
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public ModelTypeResponse create(ModelTypeCreationRequest request) {
        try {
            ModelType newModelType = ModelTypeMapper.fromModelTypeCreationRequestToEntity(request);
            newModelType = modelTypeRepository.save(newModelType);
            redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_MODEL_TYPE, newModelType.getId(), newModelType);
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
            ModelType modelTypeByIdWithRedis = (ModelType) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_MODEL_TYPE, id);

            if (!Objects.isNull(modelTypeByIdWithRedis)) {
                modelTypeByIdWithRedis.setName(request.getName());
                modelTypeByIdWithRedis.setImage(request.getImage());
                modelTypeByIdWithRedis.setDescription(request.getDescription());

                modelTypeRepository.save(modelTypeByIdWithRedis);

                redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_MODEL_TYPE, id, modelTypeByIdWithRedis);

                return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeByIdWithRedis);
            }

            ModelType modelTypeById = ModelTypeMapper.fromModelTypeResponseToEntity(findModelTypeResponseById(id));

            modelTypeById.setName(request.getName());
            modelTypeById.setImage(request.getImage());
            modelTypeById.setDescription(request.getDescription());

            modelTypeById = modelTypeRepository.save(modelTypeById);

            redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_MODEL_TYPE, id, modelTypeById);

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
            ModelType modelTypeById = ModelTypeMapper.fromModelTypeResponseToEntity(findModelTypeResponseById(id));
            redisTemplate.opsForHash().delete(ConstHashKey.HASH_KEY_MODEL_TYPE, id);
            modelTypeRepository.deleteById(modelTypeById.getId());
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
            ModelType modelTypeByIdWithRedis = (ModelType) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_MODEL_TYPE, id);

            if (!Objects.isNull(modelTypeByIdWithRedis)) {
                return modelTypeByIdWithRedis;
            }

            ModelType modelTypeById = modelTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED));

            redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_MODEL_TYPE, id, modelTypeById);

            return modelTypeById;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
        }
    }

    private ModelTypeResponse findModelTypeResponseById(String id) {
        try {
            ModelType modelTypeByIdWithRedis = (ModelType) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_MODEL_TYPE, id);

            if (!Objects.isNull(modelTypeByIdWithRedis)) {
                return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeByIdWithRedis);
            }

            ModelType modelTypeById = modelTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED));

            redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_MODEL_TYPE, id, modelTypeById);

            return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeById);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
        }
    }
}
