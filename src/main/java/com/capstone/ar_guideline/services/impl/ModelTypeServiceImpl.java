package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.ModelTypeMapper;
import com.capstone.ar_guideline.repositories.ModelTypeRepository;
import com.capstone.ar_guideline.services.IModelTypeService;
import com.capstone.ar_guideline.util.UtilService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PagingModel<ModelTypeResponse> getAll(int page, int size) {
        try {
            PagingModel<ModelTypeResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<ModelType> modelTypes = modelTypeRepository.getAlL(pageable);

            List<ModelTypeResponse> modelTypeResponses =
                    modelTypes.stream().map(ModelTypeMapper::fromEntityToModelTypeResponse).toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) modelTypes.getTotalElements());
            pagingModel.setTotalPages(modelTypes.getTotalPages());
            pagingModel.setObjectList(modelTypeResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
        }
    }

    @Override
    public ModelTypeResponse create(ModelTypeCreationRequest request) {
        try {
            ModelType modelTypeById = modelTypeRepository.findByName(request.getName());

            if (!Objects.isNull(modelTypeById)) {
                throw new AppException(ErrorCode.MACHINE_TYPE_NAME_EXISTED);
            }

            ModelType newModelType = ModelTypeMapper.fromModelTypeCreationRequestToEntity(request);
            newModelType = modelTypeRepository.save(newModelType);

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

    @Override
    public List<ModelTypeResponse> getModelTypeByCompanyId(String companyId) {
        try {
            List<ModelType> modelTypeByCompanyId = modelTypeRepository.findByCompanyId(companyId);

            return modelTypeByCompanyId.stream().map(ModelTypeMapper::fromEntityToModelTypeResponse).toList();
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
