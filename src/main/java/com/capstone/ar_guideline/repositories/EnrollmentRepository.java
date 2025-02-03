package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Enrollment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
  Integer countByCourseId(String courseId);

  @Query(
      value =
          "SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.enrollmentDate IS NULL ORDER BY e.createdDate ASC")
  List<Enrollment> findByUserIdAndEnrollmentDateNull(@Param("userId") String userId);

  @Query(
      "SELECT e FROM Enrollment e WHERE e.user.id = :userId "
          + "AND (:isNull = true AND e.enrollmentDate IS NULL OR :isNull = false AND e.enrollmentDate IS NOT NULL) "
          + "ORDER BY e.createdDate ASC")
  List<Enrollment> findByUserIdAndEnrollmentDate(
      @Param("userId") String userId, @Param("isNull") boolean isNull);
}
