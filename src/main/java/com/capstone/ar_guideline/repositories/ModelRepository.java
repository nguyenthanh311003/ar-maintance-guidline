package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, String> {
  @Query(
      "SELECT m FROM Model m WHERE m.company.id = :companyId "
          + "AND (:type IS NULL OR :type = '' OR m.modelType.name = :type) "
          + "AND (:name IS NULL OR :name = '' OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
          + "ORDER BY m.createdDate ASC")
  Page<Model> findByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("type") String type,
      @Param("name") String name);
}
