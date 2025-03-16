package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanyRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
  List<CompanyRequest> findByCompany_Id(String companyId);

  List<CompanyRequest> findByDesigner_Id(String designerId);

  CompanyRequest findByRequestId(String requestId);
}
