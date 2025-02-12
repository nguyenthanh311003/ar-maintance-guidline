package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Enrollment;
import com.capstone.ar_guideline.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
  Integer countByCourseId(String courseId);

  @Query(
      "SELECT e FROM Enrollment e WHERE e.user.id = :userId "
          + "AND (:isNull = true AND e.enrollmentDate IS NULL OR :isNull = false AND e.enrollmentDate IS NOT NULL) "
          + "ORDER BY e.createdDate ASC")
  List<Enrollment> findByUserIdAndEnrollmentDate(
      Pageable pageable, @Param("userId") String userId, @Param("isNull") boolean isNull);

  @Query(value = "SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.course.id = :courseId")
  Optional<Enrollment> findByUserId(
      @Param("userId") String userId, @Param("courseId") String courseId);

  @Query(value = "SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.course.id = :courseId " +
                 "AND e.enrollmentDate IS NOT NULL")
  Optional<Enrollment> findByUserIdAndCourseIdToCheckIsEnrolled(
          @Param("userId") String userId, @Param("courseId") String courseId);

  @Query(value = "SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
  List<Enrollment> findByCourseId(@Param("courseId") String courseId);

  Integer countByCourseIdAndEnrollmentDateIsNotNull(String courseId);

  Enrollment findByCourseIdAndUserId(String courseId, String userId);

  boolean existsByUserAndCourse(User user, Course course);
}
