package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Model;
import java.util.List;
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
          + "AND (:type IS NULL OR :type = '' OR LOWER(m.modelType.name) LIKE LOWER(CONCAT('%', :type, '%'))) "
          + "AND (:name IS NULL OR :name = '' OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
          + "AND (:code IS NULL OR :code = '' OR LOWER(m.modelCode) LIKE LOWER(CONCAT('%', :code, '%')))"
          + "ORDER BY m.createdDate ASC")
  Page<Model> findByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("type") String type,
      @Param("name") String name,
      @Param("code") String code);

  @Query(
      value =
          "SELECT m FROM Model m WHERE m.isUsed = false AND m.company.id = :companyId ORDER BY m.createdDate ASC")
  List<Model> getModelUnused(@Param("companyId") String companyId);

  @Query(value = "SELECT m FROM Model m WHERE m.company.id = :companyId ORDER BY m.createdDate ASC")
  List<Model> findAllByCompanyId(@Param("companyId") String companyId);
}
