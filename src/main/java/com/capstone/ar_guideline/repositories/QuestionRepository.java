package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {}
