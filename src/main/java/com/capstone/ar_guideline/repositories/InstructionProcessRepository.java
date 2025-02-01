package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.InstructionProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructionProcessRepository extends JpaRepository<InstructionProcess, String> {

    @Query("SELECT ip FROM InstructionProcess ip JOIN InstructionLesson il ON ip.instruction.code = il.instructionCode WHERE il.lesson.course.id = :courseId AND ip.user.id = :userId")
List<InstructionProcess> findByUserIdAndInstruction(@Param("userId") String userId,@Param("courseId") String courseId);

}
