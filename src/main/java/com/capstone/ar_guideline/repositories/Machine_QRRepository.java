package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Machine_QR;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Machine_QRRepository extends JpaRepository<Machine_QR, String> {
  @Query(
      value =
          "SELECT mqr FROM Machine_QR mqr WHERE mqr.machine.id = :machineId ORDER BY mqr.createdDate DESC")
  List<Machine_QR> getByMachineId(@Param("machineId") String machineId);

    @Query(
        value = "SELECT COUNT(mqr) FROM Machine_QR mqr WHERE mqr.machine.id = :machineId GROUP BY mqr.machine.id")
    Integer countMachineQrByMachineId(@Param("machineId") String machineId);
}
