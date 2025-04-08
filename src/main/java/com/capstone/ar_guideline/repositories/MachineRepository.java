package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Machine;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
  @Query(
      value =
          "SELECT m FROM Machine m WHERE m.company.id = :companyId "
              + "AND (:keyword IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
              + "OR (:keyword IS NULL OR LOWER(m.machineCode) LIKE LOWER(CONCAT('%', :keyword, '%')))) "
              + "AND (m.modelType.name = :machineTypeName OR :machineTypeName IS NULL) "
              + "ORDER BY m.createdDate DESC")
  Page<Machine> getMachineCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("keyword") String keyword,
      @Param("machineTypeName") String machineTypeName);

  @Query(value = "SELECT m FROM Machine m WHERE m.name = :name")
  Machine getMachineByName(@Param("name") String name);

  @Query(
      value =
          "SELECT m FROM Machine m WHERE m.modelType.id = :machineTypeId ORDER BY m.createdDate DESC")
  List<Machine> getMachineByMachineTypeId(@Param("machineTypeId") String machineTypeId);

  @Query(value = "SELECT m FROM Machine m WHERE m.machineCode = :machineCode")
  Machine getMachineByMachineCode(@Param("machineCode") String machineCode);

  @Query(
      value =
          "SELECT m FROM Machine m WHERE m.machineCode = :machineCode AND m.company.id = :companyId")
  List<Machine> getMachineByMachineCodeAndCompanyId(
      @Param("machineCode") String machineCode, @Param("companyId") String companyId);

  @Query(
      value =
          "SELECT COUNT(m) FROM Machine m WHERE m.modelType.id = :machineTypeId GROUP BY m.modelType.id")
  Integer countByModelType_Id(@Param("machineTypeId") String machineTypeId);

  @Query(
      "SELECT m FROM Machine m WHERE m.modelType.id = (SELECT g.modelType.id FROM Course g WHERE g.id = :guidelineId)")
  List<Machine> getMachineByGuidelineId(@Param("guidelineId") String guidelineId);
}
