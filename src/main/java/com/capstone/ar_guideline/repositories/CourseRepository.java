package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Course;
import java.util.List;
import java.util.Optional;
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

  @Query(
      "SELECT e.course FROM Enrollment e WHERE e.user.id = :userId AND (e.course.isMandatory = :isMandatory or e.course.isMandatory IS NULL) AND (e.course.title = :searchTemp OR e.course.description = :searchTemp OR :searchTemp IS NULL) AND (e.course.status = :status OR :status IS NULL)")
  List<Course> findAllCourseEnrolledBy(
      Pageable pageable,
      @Param("isMandatory") Boolean isMandatory,
      @Param("userId") String userId,
      @Param("searchTemp") String searchTemp,
      @Param("status") String status);

  Optional<Course> findByTitle(String title);

  @Query(
      value = "SELECT c FROM Course c WHERE c.company.id = :companyId ORDER BY c.createdDate DESC")
  List<Course> findByCompanyId(@Param("companyId") String companyId);

  @Query(
      value =
          "SELECT c FROM Course c WHERE c.isMandatory = false AND c.status = 'ACTIVE' AND c.company.id = :companyId ORDER BY c.createdDate ASC")
  List<Course> findCourseNoMandatory(Pageable pageable, @Param("companyId") String companyId);

  @Query(value = "SELECT c FROM Course c WHERE c.courseCode = :courseCode AND c.status = 'ACTIVE'")
  Course findByCode(@Param("courseCode") String courseCode);

  @Query(value = "SELECT c FROM Course c WHERE c.model.id = :modelId")
  Course findByModelId(@Param("modelId") String modelId);
}
