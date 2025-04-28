//package com.capstone.ar_guideline.repositories;
//
//import com.capstone.ar_guideline.entities.MachineTypeValue;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface MachineTypeValueRepository extends JpaRepository<MachineTypeValue, String> {
//  @Query(
//      value =
//          "SELECT mtv FROM MachineTypeValue mtv WHERE mtv.machineTypeAttribute.id = :machineTypeAttributeId ")
//  MachineTypeValue getByMachineTypeAttributeId(
//      @Param("machineTypeAttributeId") String machineTypeAttributeId,
//      @Param("machineId") String machineId);
//
//  @Query(
//      value =
//          "SELECT mtv FROM MachineTypeValue mtv WHERE mtv.machineTypeAttribute.id = :machineTypeAttributeId ")
//  MachineTypeValue getByMachineTypeAttributeId(String machineTypeAttributeId);
//}
