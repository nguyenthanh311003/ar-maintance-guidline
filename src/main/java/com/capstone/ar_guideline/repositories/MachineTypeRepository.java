package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ModelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MachineTypeRepository extends JpaRepository<ModelType, String> {
  @Query(
      "SELECT mt FROM ModelType mt WHERE mt.company.id = :companyId "
          + "AND (:name = '' OR :name IS NULL OR LOWER(mt.name) LIKE LOWER(CONCAT('%', :name, '%')))"
          + "ORDER BY mt.createdDate DESC")
  Page<ModelType> getMachineTypeByCompanyId(
      Pageable pageable, @Param("companyId") String companyId, @Param("name") String name);

  @Query("SELECT c.modelType FROM Course c WHERE c.courseCode = :guidelineCode")
  ModelType getMachineTypeByGuidelineCode(@Param("guidelineCode") String guidelineCode);

  @Query(
      value =
          "SELECT COUNT(m) FROM ModelType m WHERE m.company.id = :companyId GROUP BY m.company.id")
  Integer countByCompany_Id(@Param("companyId") String companyId);

  ModelType findByNameAndCompanyId(String name, String companyId);
}
