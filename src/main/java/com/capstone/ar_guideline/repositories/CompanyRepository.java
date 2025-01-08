package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
  Optional<Company> findByCompanyName(String name);
}
