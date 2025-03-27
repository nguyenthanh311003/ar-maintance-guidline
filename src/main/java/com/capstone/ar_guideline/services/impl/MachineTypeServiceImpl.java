package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.MachineTypeRepository;
import com.capstone.ar_guideline.services.IMachineTypeService;
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
public class MachineTypeServiceImpl implements IMachineTypeService {
  MachineTypeRepository machineTypeRepository;

  @Override
  public ModelType create(ModelType machineType) {
    try {
      return machineTypeRepository.save(machineType);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_CREATE_FAILED);
    }
  }

  @Override
  public ModelType update(String id, ModelType machineType) {
    try {
      findById(id);
      return machineTypeRepository.save(machineType);
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
      ModelType modelTypeById = findById(id);

      machineTypeRepository.deleteById(modelTypeById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
    }
  }

  @Override
  public ModelType findById(String id) {
    try {
      return machineTypeRepository
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
  public Page<ModelType> getMachineTypeByCompanyId(Pageable pageable, String companyId, String name) {
    try {
      return machineTypeRepository.getMachineTypeByCompanyId(pageable, companyId, name);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
    }
  }
}
