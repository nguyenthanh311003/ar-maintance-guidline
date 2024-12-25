package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
  Optional<Company> findByCompanyName(String name);
}
