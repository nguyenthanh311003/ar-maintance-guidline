package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Instruction;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, String> {
  @Query(
      value =
          "SELECT MAX(i.orderNumber) FROM Instruction i WHERE i.course.id = :modelId GROUP BY i.course.id")
  Integer getHighestOrderNumber(@Param("modelId") String modelId);

  @Query(
      value =
          "SELECT i FROM Instruction i WHERE i.course.id = :courseId ORDER BY i.orderNumber ASC")
  List<Instruction> getByCourseId(@Param("courseId") String courseId);

  @Query(
      value =
          "SELECT i FROM Instruction i WHERE i.course.id = :courseId ORDER BY i.orderNumber ASC")
  Page<Instruction> getByCourseIdPaging(Pageable pageable, @Param("courseId") String courseId);

  Instruction findByNameAndAndCourseId(String id, String courseId);

}
