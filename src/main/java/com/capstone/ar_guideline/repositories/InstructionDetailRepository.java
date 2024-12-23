package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.InstructionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionDetailRepository extends JpaRepository<InstructionDetail, String> {}
