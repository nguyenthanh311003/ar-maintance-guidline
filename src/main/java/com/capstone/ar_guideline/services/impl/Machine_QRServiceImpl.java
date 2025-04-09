package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Machine_QR;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.Machine_QRRepository;
import com.capstone.ar_guideline.services.IMachine_QRService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Machine_QRServiceImpl implements IMachine_QRService {
  Machine_QRRepository machineQrRepository;

  @Override
  public Machine_QR create(Machine_QR machineQr) {
    try {
      return machineQrRepository.save(machineQr);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_QR_CREATE_FAILED);
    }
  }

  @Override
  public Machine_QR update(String id, Machine_QR machineQr) {
    return null;
  }

  @Override
  public void delete(String id) {
    try {
      Machine_QR machineQrById = findById(id);

      machineQrRepository.deleteById(machineQrById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_QR_DELETE_FAILED);
    }
  }

  @Override
  public Machine_QR findById(String id) {
    try {
      return machineQrRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.MACHINE_QR_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_QR_NOT_EXISTED);
    }
  }

  @Override
  public List<Machine_QR> getByMachineId(String machineId) {
    try {
      return machineQrRepository.getByMachineId(machineId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_QR_NOT_EXISTED);
    }
  }

  @Override
  public Integer countMachineQrByMachineId(String machineId) {
    try {
        return machineQrRepository.countMachineQrByMachineId(machineId);
    } catch (Exception exception) {
        if (exception instanceof AppException) {
            throw exception;
        }
        throw new AppException(ErrorCode.MACHINE_QR_NOT_EXISTED);
    }
  }
}
