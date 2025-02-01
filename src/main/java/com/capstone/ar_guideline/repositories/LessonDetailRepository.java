package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.LessonDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonDetailRepository extends JpaRepository<LessonDetail, String> {

    List<LessonDetail> findLessonDetailsByLessonId(String lessonId);


}
