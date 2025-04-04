package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.entities.MachineTypeValue;

public interface IMachineTypeValueService {
  MachineTypeValue create(MachineTypeValue machineTypeValue);

  MachineTypeValue update(String id, MachineTypeValue machine);

  void delete(String id);

  MachineTypeValue findById(String id);

  MachineTypeValue findByMachineTypeAttributeIdAndMachineId(
      String machineTypeAttributeId, String machineId);

  MachineTypeValue findByMachineTypeAttributeId(String machineTypeAttributeId);
}
