package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.InstructionDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionDetailRepository extends JpaRepository<InstructionDetail, String> {
  @Query(
      value =
          "SELECT MAX(i.orderNumber) FROM InstructionDetail i WHERE i.instruction.id = :instructionId GROUP BY i.instruction.id")
  Integer getHighestOrderNumber(@Param("instructionId") String instructionId);

  @Query(
      value =
          "SELECT i FROM InstructionDetail i WHERE i.instruction.id = :instructionId ORDER BY i.orderNumber ASC")
  List<InstructionDetail> getByInstructionId(@Param("instructionId") String instructionId);

  @Query(
      """
        SELECT id
        FROM InstructionDetail id
        WHERE id.instruction.id IN (
            SELECT i.id FROM Instruction i WHERE i.course.id = :courseId
        ) and id.status = 'DRAFTED'
        """)
  List<InstructionDetail> countInstructionDetailByCourseId(@Param("courseId") String courseId);
}
