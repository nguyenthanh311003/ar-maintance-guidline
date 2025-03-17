package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ModelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineTypeRepository extends JpaRepository<ModelType, String> {
  @Query(
      "SELECT mt FROM ModelType mt WHERE mt.company.id = :companyId ORDER BY mt.createdDate DESC")
  Page<ModelType> getMachineTypeByCompanyId(
      Pageable pageable, @Param("companyId") String companyId);
}
