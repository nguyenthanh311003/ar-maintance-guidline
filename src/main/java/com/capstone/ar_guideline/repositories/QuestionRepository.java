package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
  @Query(value = "SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.createdDate ASC")
  List<Question> findByQuizId(@Param("quizId") String quizId);
}
