package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.InstructionProcess;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructionProcessRepository extends JpaRepository<InstructionProcess, String> {

  @Query(
      "SELECT ip FROM InstructionProcess ip JOIN InstructionLesson il  WHERE il.lesson.course.id = :courseId AND ip.user.id = :userId")
  List<InstructionProcess> findByUserIdAndInstruction(
      @Param("userId") String userId, @Param("courseId") String courseId);
}
