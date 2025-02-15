package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Lesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
  Integer countByCourseId(String courseId);

  List<Lesson> findAllByCourseId(String courseId);
}
