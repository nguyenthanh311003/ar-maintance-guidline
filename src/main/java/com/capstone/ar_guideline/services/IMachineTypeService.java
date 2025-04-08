package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.entities.ModelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMachineTypeService {
  ModelType create(ModelType machineType);

  ModelType update(String id, ModelType machineType);

  void delete(String id);

  ModelType findById(String id);

  Page<ModelType> getMachineTypeByCompanyId(Pageable pageable, String companyId, String name);

  ModelType getMachineTypeByGuidelineCode(String guidelineCode);
}
