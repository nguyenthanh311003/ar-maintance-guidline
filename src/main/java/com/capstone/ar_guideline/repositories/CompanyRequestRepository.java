package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanyRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
  List<CompanyRequest> findByCompany_IdOrderByCreatedAtDesc(String companyId);

  @Query(
      value =
          "SELECT c FROM CompanyRequest c WHERE c.company.id = :companyId "
              + "AND (:status IS NULL OR LOWER(c.status) LIKE LOWER(CONCAT('%', :status, '%'))) "
              + "ORDER BY c.createdAt DESC")
  Page<CompanyRequest> findByCompanyId(
      Pageable pageable, @Param("companyId") String companyId, @Param("status") String status);

  @Query(value = "SELECT c FROM CompanyRequest c WHERE c.machineType.id = :machineTypeId")
  List<CompanyRequest> findByMachineTypeId(@Param("machineTypeId") String machineTypeId);

  List<CompanyRequest> findByDesigner_IdOrderByCreatedAtDesc(String designerId);

  CompanyRequest findByRequestId(String requestId);

  @Query(
      " SELECT c FROM CompanyRequest c "
          + "WHERE (:status IS NULL OR LOWER(c.status) LIKE LOWER(CONCAT('%', :status, '%')))"
          + "AND (:companyName IS NULL OR LOWER(c.company.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))) "
          + "AND (:designerEmail IS NULL OR LOWER(c.designer.email) LIKE LOWER(CONCAT('%', :designerEmail, '%'))) "
          + "ORDER BY c.createdAt DESC")
  Page<CompanyRequest> findAllForDesigner(
      Pageable pageable,
      @Param("status") String status,
      @Param("companyName") String companyName,
      @Param("designerEmail") String designerEmail);

  @Query("SELECT c.requestNumber FROM CompanyRequest c")
  List<String> findAllRequestNumbers();
}
