package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
  Quiz findByCourseId(String courseId);
}
