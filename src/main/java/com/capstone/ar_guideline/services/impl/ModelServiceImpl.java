package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstCommon;
import com.capstone.ar_guideline.constants.ConstStatus;
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
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelServiceImpl implements IModelService {
  ModelRepository modelRepository;
  IModelTypeService modelTypeService;

  @Override
  @Transactional
  public ModelResponse create(ModelCreationRequest request) throws InterruptedException {
    try {

      Model modelByName = modelRepository.findByName(request.getName());

      if (!Objects.isNull(modelByName)) {
        throw new AppException(ErrorCode.MODEL_NAME_EXISTED);
      }

      Model newModel = ModelMapper.fromModelCreationRequestToEntity(request);
      ModelType modelType = modelTypeService.findById(request.getModelTypeId());
      newModel.setImageUrl(
          request.getImageUrl() != null
              ? FileStorageService.storeFile(request.getImageUrl())
              : null);
      newModel.setFile(FileStorageService.storeFile(request.getFile()));
      newModel.setIsUsed(false);
      newModel.setStatus(ConstStatus.INACTIVE_STATUS);
      newModel.setSize((double) request.getFile().getSize() / ConstCommon.fileUnit);
      newModel.setSize((double) request.getFile().getSize());
      newModel.setPosition(
          request.getPosition().stream().map(String::valueOf).collect(Collectors.joining(",")));

      newModel.setRotation(
          request.getRotation().stream().map(String::valueOf).collect(Collectors.joining(",")));
      newModel.setModelType(modelType);
      newModel = modelRepository.save(newModel);

      return ModelMapper.fromEntityToModelResponse(newModel);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_CREATE_FAILED);
    }
  }

  @Override
  public Model create(Model model) throws InterruptedException {
    try {
      return modelRepository.save(model);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_CREATE_FAILED);
    }
  }

  @Override
  public Model update(Model model) {
    try {
      return modelRepository.save(model);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_UPDATE_FAILED);
    }
  }

  @Override
  public ModelResponse update(String id, ModelCreationRequest request) {
    try {
      Model modelById = findById(id);
      modelById.setStatus(request.getStatus());
      modelById.setModelCode(request.getModelCode());
      modelById.setName(request.getName());
      modelById.setDescription(request.getDescription());
      modelById.setVersion(request.getVersion());
      modelById.setScale(request.getScale());
      if (request.getImageUrl() != null) {
        modelById.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      }
      if (request.getFile() != null) {
        modelById.setFile(FileStorageService.storeFile(request.getFile()));

        modelById.setSize((double) request.getFile().getSize() / ConstCommon.fileUnit);
      }

      modelById = modelRepository.save(modelById);

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
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_DELETE_FAILED);
    }
  }

  @Override
  public Model findById(String id) {
    try {
      return modelRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.MODEL_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public Page<Model> findByCompanyId(
      Pageable pageable,
      String companyId,
      String type,
      String name,
      String code,
      String machineTypeId) {
    try {
      return modelRepository.findByCompanyId(pageable, companyId, type, name, code, machineTypeId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public List<ModelResponse> getModelUnused(String companyId) {
    try {
      List<Model> models = modelRepository.getModelUnused(companyId);

      return models.stream().map(ModelMapper::fromEntityToModelResponse).toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public Boolean updateIsUsed(boolean isCreate, Model model) {
    try {
      model.setIsUsed(isCreate);
      model = modelRepository.save(model);

      return model.getId() != null;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_UPDATE_FAILED);
    }
  }

  @Override
  public ModelResponse getByCourseId(String courseId) {
    try {
      Model modelByCourseId = modelRepository.getByCourseId(courseId);

      return ModelMapper.fromEntityToModelResponse(modelByCourseId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public ModelResponse findByIdResponse(String id) {
    try {
      Model model = findById(id);
      return ModelMapper.fromEntityToModelResponse(model);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public List<Model> findAllByCompanyId(String companyId) {
    try {
      return modelRepository.findAllByCompanyId(companyId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public void updateIsUsedByCourseId(String courseId) {
    try {
      Model modelById = modelRepository.getByCourseId(courseId);

      modelById.setIsUsed(!modelById.getIsUsed());

      modelRepository.save(modelById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_UPDATE_FAILED);
    }
  }

  @Override
  public void changeStatus(String modelId) {
    Model modelById = modelRepository.findById(modelId).get();
    if (ConstStatus.ACTIVE_STATUS.equals(modelById.getStatus())) {
      modelById.setStatus(ConstStatus.INACTIVE_STATUS);
    } else {
      modelById.setStatus(ConstStatus.ACTIVE_STATUS);
    }
    modelRepository.save(modelById);
  }

  @Override
  public List<Model> getModelsByMachineTypeIdAndCompanyId(String machineTypeId, String companyId) {
    try {
      return modelRepository.findAllByMachineTypeIdAndCompanyId(machineTypeId, companyId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }
}
