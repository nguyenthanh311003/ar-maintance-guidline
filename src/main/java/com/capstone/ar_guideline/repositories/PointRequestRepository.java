package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.PointRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRequestRepository extends JpaRepository<PointRequest, String> {
  List<PointRequest> findByCompanyIdOrderByCreatedAtDesc(String companyId);

  List<PointRequest> findByEmployeeIdOrderByCreatedAtDesc(String employeeId);

  @Query("SELECT p.requestNumber FROM PointRequest p")
  List<String> findAllRequestNumbers();
}
