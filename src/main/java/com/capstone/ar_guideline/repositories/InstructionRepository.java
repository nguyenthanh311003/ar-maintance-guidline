package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Instruction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, String> {
  @Query(
      value =
          "SELECT MAX(i.orderNumber) FROM Instruction i WHERE i.model.id = :modelId GROUP BY i.model.id")
  Integer getHighestOrderNumber(@Param("modelId") String modelId);

  @Query(
      value = "SELECT i FROM Instruction i WHERE i.model.id = :modelId ORDER BY i.orderNumber ASC")
  List<Instruction> getByModelId(@Param("modelId") String modelId);
}
