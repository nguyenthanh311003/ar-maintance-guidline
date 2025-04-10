package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.RequestRevision;
import com.capstone.ar_guideline.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRevisionRepository extends JpaRepository<RequestRevision, UUID> {


    List<RequestRevision> findAllByCompanyRequestRequestId(String companyRequestId);

}
