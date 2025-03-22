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
          + "AND (:name IS NULL OR :name = '' OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
          + "AND (:code IS NULL OR :code = '' OR LOWER(m.modelCode) LIKE LOWER(CONCAT('%', :code, '%')))"
          + "AND (m.status <> 'DRAFT') "
          + "ORDER BY m.createdDate DESC")
  Page<Model> findByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("type") String type,
      @Param("name") String name,
      @Param("code") String code);

  @Query(
      value =
          "SELECT m FROM Model m WHERE m.isUsed = false AND m.company.id = :companyId AND m.status = 'ACTIVE' ORDER BY m.createdDate DESC ")
  List<Model> getModelUnused(@Param("companyId") String companyId);

  @Query(value = "SELECT m FROM Model m JOIN Course c ON m.id = c.model.id WHERE c.id = :courseId ")
  Model getByCourseId(@Param("courseId") String courseId);

  @Query(
      value = "SELECT m FROM Model m WHERE m.company.id = :companyId ORDER BY m.createdDate DESC")
  List<Model> findAllByCompanyId(@Param("companyId") String companyId);

  @Query(value = "SELECT m FROM Model m WHERE m.name = :modelName")
  Model findByName(@Param("modelName") String modelName);

  @Query(
      value =
          "SELECT COUNT(m) FROM Model m WHERE (m.company.id = :companyId OR :companyId IS NULL) AND (m.status = :status OR m.status IS NULL )")
  Integer countAllBy(String companyId, String status);
}
