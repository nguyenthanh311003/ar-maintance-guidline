package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Company;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
  Optional<Company> findByCompanyName(String name);

  @Query("SELECT c FROM Company c JOIN c.users u WHERE u.id = :userId")
  Optional<Company> findByUserId(String userId);

  @Query(
      "SELECT c FROM Company c "
          + "WHERE (:companyName IS NULL OR LOWER(c.companyName) LIKE LOWER(CONCAT('%', :companyName, '%')))"
          + "ORDER BY c.createdDate DESC")
  Page<Company> getCompanies(Pageable pageable, @Param("companyName") String companyName);
}
