package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.MachineTypeAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineTypeAttributeRepository extends JpaRepository<MachineTypeAttribute, String> {
    @Query(value = "SELECT mta FROM MachineTypeAttribute mta WHERE mta.modelType.id = :machineTypeId")
    List<MachineTypeAttribute> getMachineTypeAttributeByMachineTypeId(@Param("machineTypeId") String machineTypeId);
}
