package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanyRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
  List<CompanyRequest> findByCompany_IdOrderByCreatedAtDesc(String companyId);

  List<CompanyRequest> findByDesigner_IdOrderByCreatedAtDesc(String designerId);

  CompanyRequest findByRequestId(String requestId);

  @Query(" select c from CompanyRequest c order by c.createdAt desc ")
  List<CompanyRequest> findAll();

  @Query("SELECT MAX(c.requestNumber) FROM CompanyRequest c")
  Long findMaxRequestNumber();
}
