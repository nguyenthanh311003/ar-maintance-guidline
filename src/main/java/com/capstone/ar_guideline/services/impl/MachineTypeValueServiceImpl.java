package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.MachineTypeValue;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.MachineTypeValueRepository;
import com.capstone.ar_guideline.services.IMachineTypeValueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MachineTypeValueServiceImpl implements IMachineTypeValueService {
    MachineTypeValueRepository machineTypeValueRepository;

    @Override
    public MachineTypeValue create(MachineTypeValue machineTypeValue) {
        try {
            return machineTypeValueRepository.save(machineTypeValue);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_VALUE_CREATE_FAILED);
        }
    }

    @Override
    public MachineTypeValue update(String id, MachineTypeValue machine) {
        try {
            findById(id);

            return machineTypeValueRepository.save(machine);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_VALUE_UPDATE_FAILED);
        }
    }

    @Override
    public void delete(String id) {
        try {
            MachineTypeValue machineTypeValueById = findById(id);

            machineTypeValueRepository.deleteById(machineTypeValueById.getId());
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_VALUE_DELETE_FAILED);
        }
    }

    @Override
    public MachineTypeValue findById(String id) {
        try {
            return machineTypeValueRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.MACHINE_TYPE_VALUE_NOT_EXISTED));
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_VALUE_NOT_EXISTED);
        }
    }

    @Override
    public MachineTypeValue findByMachineTypeAttributeIdAndMachineId(String machineTypeAttributeId, String machineId) {
        try {
            return machineTypeValueRepository.getByMachineTypeAttributeId(machineTypeAttributeId, machineId);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_VALUE_NOT_EXISTED);
        }
    }
}
