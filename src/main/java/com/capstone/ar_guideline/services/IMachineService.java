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

  Machine findByCodeAndCompanyId(String machineCode, String companyId);

  Page<Machine> getMachineByCompanyId(
      Pageable pageable, String companyId, String keyword, String machineTypeName);

  Machine getMachineByName(String name);

  List<Machine> getMachineByMachineType(String machineTypeId);

  Boolean isMachineCodeExisted(String companyId, String machineCode);

  Boolean isMachineCodeExistedForUpdate(
      String companyId, String machineCode, String machineCodeByMachineId);

  Integer countMachineByMachineType(String machineTypeId);

  List<Machine> getMachineByGuidelineId(String guidelineId);

  Integer countMachineByCompanyId(String companyId);

  Boolean checkMachineIsBelongToGuideline(String machineCode, String guidelineId);
}
