package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanyRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
  List<CompanyRequest> findByCompany_IdOrderByCreatedAtDesc(String companyId);

  List<CompanyRequest> findByDesigner_IdOrderByCreatedAtDesc(String designerId);

  CompanyRequest findByRequestId(String requestId);
}
