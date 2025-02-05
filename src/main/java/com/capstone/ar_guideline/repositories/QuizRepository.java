package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
  Quiz findByCourseId(String courseId);

  @Query(
      value =
          "SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Quiz q WHERE q.courseId = :courseId")
  boolean existsByCourseId(@Param("courseId") String courseId);
}
