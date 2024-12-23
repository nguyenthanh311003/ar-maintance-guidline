package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, String> {}
