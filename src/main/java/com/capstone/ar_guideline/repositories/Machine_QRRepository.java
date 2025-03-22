package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Machine_QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Machine_QRRepository extends JpaRepository<Machine_QR, String> {
    @Query(value = "SELECT mqr FROM Machine_QR mqr WHERE mqr.machine.id = :machineId ORDER BY mqr.createdDate DESC")
    List<Machine_QR> getByMachineId(@Param("machineId") String machineId);
}
