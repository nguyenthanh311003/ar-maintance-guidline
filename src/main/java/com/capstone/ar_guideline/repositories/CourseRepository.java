package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Course;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

  @Query(
      "SELECT c FROM Course c WHERE (c.title = :searchTemp OR c.description = :searchTemp OR :searchTemp IS NULL) AND (c.status = :status OR :status IS NULL)")
  List<Course> findAllBy(
      Pageable pageable, @Param("searchTemp") String searchTemp, @Param("status") String status);
}
