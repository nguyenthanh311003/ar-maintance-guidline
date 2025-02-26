package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.configurations.AppConfig;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelServiceImpl implements IModelService {
  ModelRepository modelRepository;
  IModelTypeService modelTypeService;
  VuforiaService vuforiaService;

  private final AppConfig appConfig;

  @Override
  @Transactional
  public ModelResponse create(ModelCreationRequest request) throws InterruptedException {
    try {
      ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

      Model newModel = ModelMapper.fromModelCreationRequestToEntity(request);
      newModel.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      newModel.setFile(FileStorageService.storeFile(request.getFile()));
      newModel.setIsUsed(false);
      newModel.setStatus(ConstStatus.ACTIVE_STATUS);
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
  public ModelResponse update(String id, ModelCreationRequest request) {
    try {
      Model modelById = findById(id);
      modelById = ModelMapper.fromModelCreationRequestToEntity(request);
      if (request.getImageUrl() != null) {
        modelById.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      }
      if (request.getFile() != null) {
        modelById.setFile(FileStorageService.storeFile(request.getFile()));
      }
      ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

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
    return modelRepository
        .findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.MODEL_NOT_EXISTED));
  }

  @Override
  public PagingModel<ModelResponse> findByCompanyId(
      int page, int size, String companyId, String type, String name, String code) {
    try {
      PagingModel<ModelResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<Model> models = modelRepository.findByCompanyId(pageable, companyId, type, name, code);
      List<ModelResponse> modelResponses =
          models.stream()
              .map(
                  m ->
                      ModelResponse.builder()
                          .id(m.getId())
                          .modelTypeId(m.getModelType().getId())
                          .modelCode(m.getModelCode())
                          .status(m.getStatus())
                          .name(m.getName())
                          .description(m.getDescription())
                          .imageUrl(m.getImageUrl())
                          .version(m.getVersion())
                          .modelTypeName(m.getModelType().getName())
                          .scale(m.getScale())
                          .file(m.getFile())
                          .build())
              .toList();

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) models.getTotalElements());
      pagingModel.setTotalPages(models.getTotalPages());
      pagingModel.setObjectList(modelResponses);
      return pagingModel;
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
}
