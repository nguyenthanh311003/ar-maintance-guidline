package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.entities.Machine;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMachineService {
  Machine create(Machine machine);

  Machine update(String id, Machine machine);

  void delete(String id);

  Machine findById(String id);

  Machine findByCode(String machineCode);

  Page<Machine> getMachineByCompanyId(
      Pageable pageable, String companyId, String keyword, String machineTypeName);

  Machine getMachineByName(String name);

  List<Machine> getMachineByMachineType(String machineTypeId);

  Boolean isMachineCodeExisted(String companyId, String machineCode);

  Integer countMachineByMachineType(String machineTypeId);
}
