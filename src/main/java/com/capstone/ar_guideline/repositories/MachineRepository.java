package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
  @Query(
      value = "SELECT m FROM Machine m WHERE m.company.id = :companyId ORDER BY m.createdDate DESC")
  Page<Machine> getMachineCompanyId(Pageable pageable, @Param("companyId") String companyId);

  @Query(value = "SELECT m FROM Machine m WHERE m.name = :name")
  Machine getMachineByName(@Param("name") String name);
}
