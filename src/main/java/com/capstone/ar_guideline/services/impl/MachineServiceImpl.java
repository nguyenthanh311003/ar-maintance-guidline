package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Machine;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.MachineRepository;
import com.capstone.ar_guideline.services.IMachineService;
import java.util.List;
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
public class MachineServiceImpl implements IMachineService {
  MachineRepository machineRepository;

  @Override
  public Machine create(Machine machine) {
    try {
      return machineRepository.save(machine);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_CREATE_FAILED);
    }
  }

  @Override
  public Machine update(String id, Machine machine) {
    try {
      findById(id);
      return machineRepository.save(machine);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Machine machineById = findById(id);

      machineRepository.deleteById(machineById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_DELETE_FAILED);
    }
  }

  @Override
  public Machine findById(String id) {
    try {
      return machineRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public Page<Machine> getMachineByCompanyId(Pageable pageable, String companyId) {
    try {
      return machineRepository.getMachineCompanyId(pageable, companyId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public Machine getMachineByName(String name) {
    try {
      return machineRepository.getMachineByName(name);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public List<Machine> getMachineByMachineType(String machineTypeId) {
    try {
      return machineRepository.getMachineByMachineTypeId(machineTypeId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }
}
