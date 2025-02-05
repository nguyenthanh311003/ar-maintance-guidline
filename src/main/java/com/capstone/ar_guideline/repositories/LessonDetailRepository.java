package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.LessonDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonDetailRepository extends JpaRepository<LessonDetail, String> {

  List<LessonDetail> findLessonDetailsByLessonId(String lessonId);
}
