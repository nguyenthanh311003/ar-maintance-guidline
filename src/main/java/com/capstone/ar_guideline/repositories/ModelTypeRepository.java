package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.ModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, String> {

}
