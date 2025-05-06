package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.RequestRevision;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRevisionRepository extends JpaRepository<RequestRevision, UUID> {

  List<RequestRevision> findAllByCompanyRequestRequestIdOrderByCreatedDateDesc(String companyRequestId);
}
