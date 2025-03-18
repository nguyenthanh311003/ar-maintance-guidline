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

  Page<Machine> getMachineByCompanyId(Pageable pageable, String companyId);

  Machine getMachineByName(String name);

  List<Machine> getMachineByMachineType(String machineTypeId);
}
