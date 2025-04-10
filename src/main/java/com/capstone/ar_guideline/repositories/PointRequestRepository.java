package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.PointRequest;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRequestRepository extends JpaRepository<PointRequest, String> {
  @Query(
      value =
          "SELECT pr FROM PointRequest pr WHERE pr.company.id = :companyId "
              + "AND (:requestNumber IS NULL OR LOWER(pr.requestNumber) LIKE LOWER(CONCAT('%', :requestNumber, '%'))) "
              + "AND (:status IS NULL OR pr.status = :status) "
              + "AND (:employeeEmail IS NULL OR LOWER(pr.employee.email) LIKE LOWER(CONCAT('%', :employeeEmail, '%'))) "
              + "AND (:createDate IS NULL OR FUNCTION('DATE', pr.createdAt) = :createDate) "
              + "ORDER BY pr.createdAt DESC")
  Page<PointRequest> findByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("requestNumber") String requestNumber,
      @Param("status") String status,
      @Param("employeeEmail") String employeeEmail,
      @Param("createDate") LocalDate createDate);

  List<PointRequest> findByEmployeeIdOrderByCreatedAtDesc(String employeeId);

  @Query("SELECT p.requestNumber FROM PointRequest p")
  List<String> findAllRequestNumbers();
}
