package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
  @Query(
      "SELECT c FROM Course c WHERE (c.title = :searchTemp OR c.description = :searchTemp OR :searchTemp IS NULL) AND (c.status = :status OR :status IS NULL) AND (c.company.id = :companyId OR :companyId IS NULL)")
  List<Course> findAllBy(
      Pageable pageable,
      @Param("searchTemp") String searchTemp,
      @Param("status") String status,
      @Param("companyId") String companyId);

  Optional<Course> findByTitle(String title);

  @Query(value = "SELECT c FROM Course c WHERE c.company.id = :companyId AND c.model.id = :modelId")
  List<Course> findByModelIdAndCompanyId(
      @Param("modelId") String modelId, @Param("companyId") String companyId);

  @Query(value = "SELECT c FROM Course c WHERE c.modelType.id = :machineTypeId")
  List<Course> findByMachineTypeId(@Param("machineTypeId") String machineTypeId);

  @Query(
      value =
          "SELECT c FROM Course c WHERE c.company.id = :companyId "
              + "AND (:title = '' OR :title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) "
              + "AND (:status = '' OR :status IS NULL OR c.status = :status) "
              + "AND (:machineTypeId = '' OR :machineTypeId IS NULL OR c.modelType.id = :machineTypeId) "
              + "ORDER BY c.createdDate DESC")
  Page<Course> findByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("title") String title,
      @Param("status") String status,
      @Param("machineTypeId") String machineTypeId);

  @Query(
      value =
          "SELECT c FROM Course c WHERE c.isMandatory = false AND c.status = 'ACTIVE' AND c.company.id = :companyId ORDER BY c.createdDate ASC")
  List<Course> findCourseNoMandatory(Pageable pageable, @Param("companyId") String companyId);

  @Query(value = "SELECT c FROM Course c WHERE c.courseCode = :courseCode AND c.status = 'ACTIVE'")
  Course findByCode(@Param("courseCode") String courseCode);

  @Query(value = "SELECT c FROM Course c WHERE c.model.id = :modelId")
  Course findByModelId(@Param("modelId") String modelId);

  @Query(value = "SELECT c FROM Course c WHERE c.model.id = :modelId")
  List<Course> findByModelIdReturnList(@Param("modelId") String modelId);

  @Query(
      value =
          "SELECT COUNT(c) FROM Course c WHERE (c.company.id = :companyId OR :companyId IS NULL) AND (c.status = :status OR c.status IS NULL )")
  Integer countAllBy(String companyId, String status);

  @Query(
      value =
          "SELECT c FROM Course c WHERE (c.company.id = :companyId OR :companyId IS NULL) ORDER BY c.numberOfScan DESC",
      nativeQuery = false)
  List<Course> findTop3CoursesByScanTimes(Pageable pageable, @Param("companyId") String companyId);

  Long countByCompany_Id(String companyId);

  @Query(
      value =
          "SELECT c FROM Course c WHERE c.modelType.id = :machineTypeId AND c.company.id = :companyId")
  List<Course> findByMachineTypeIdAndCompanyId(
      @Param("machineTypeId") String machineTypeId, @Param("companyId") String companyId);
}
