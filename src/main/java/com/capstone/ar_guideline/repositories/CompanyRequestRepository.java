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

  List<CompanyRequest> findByDesigner_IdOrderByCreatedAtDesc(String designerId);

  CompanyRequest findByRequestId(String requestId);

  @Query(" select c from CompanyRequest c order by c.createdAt desc ")
  List<CompanyRequest> findAll();

  @Query("SELECT c.requestNumber FROM CompanyRequest c")
  List<String> findAllRequestNumbers();
}
