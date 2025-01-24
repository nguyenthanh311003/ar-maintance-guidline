package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, String> {
  @Query(value = "SELECT o FROM Option o WHERE o.question.id = :questionId")
  List<Option> findByQuestionId(@Param("questionId") String questionId);
}
