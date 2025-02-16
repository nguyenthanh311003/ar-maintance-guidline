package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.configurations.AppConfig;
import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Vuforia.DatasetRequest;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.Vuforia.DataStatusResponse;
import com.capstone.ar_guideline.dtos.responses.Vuforia.DatasetStatusResponse;
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

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
//      newModel.setFile(FileStorageService.storeFile(request.getFile()));
//      newModel.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      String fileUrl = appConfig.getApplicationUrl()+ "/"+ConstAPI.FileAPI.FILE+"/"+newModel.getFile();
      DatasetRequest datasetRequest = new DatasetRequest(

              "dataset-name",
              "10.18",
              Arrays.asList(
                      new DatasetRequest.ModelData(
                              "mouse",
                              fileUrl,
                              Arrays.asList(
                                      new DatasetRequest.ViewData(
                                              "viewpoint-name",
                                              "landscape",
                                              new DatasetRequest.GuideViewPosition(
                                                      Arrays.asList(0f, 0f, 5f),
                                                      Arrays.asList(0f, 0f, 0f, 1f)
                                              )
                                      )
                              )
                      )
              )
      );

   // DataStatusResponse dataStatusResponse =  vuforiaService.createDataset(datasetRequest);
      DataStatusResponse dataStatusResponse =  new DataStatusResponse("e6a9a4dff6d34b179eb4c17d2cec675b");

      while(true){
     DatasetStatusResponse datasetStatus = vuforiaService.getDatasetStatus(dataStatusResponse.getUuid()).block();
     Thread.sleep(5000);
     if(datasetStatus.getStatus().equals("done")){
  String id = vuforiaService.downloadAndStoreDataset(datasetStatus.getUuid()).block();
       break;
     }
    }
    newModel.setModelCode(dataStatusResponse.getUuid());
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
}
