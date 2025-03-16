package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.entities.Machine;
import com.capstone.ar_guideline.entities.MachineTypeAttribute;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMachineTypeAttributeService {
    MachineTypeAttribute create(MachineTypeAttribute machineTypeAttribute);

    MachineTypeAttribute update(String id, MachineTypeAttribute machineTypeAttribute);

    void delete(String id);

    MachineTypeAttribute findById(String id);

    List<MachineTypeAttributeResponse> getMachineTypeAttributeByMachineTypeId(String machineTypeId);

    List<MachineTypeAttribute> getByMachineTypeId(String machineTypeId);
}
