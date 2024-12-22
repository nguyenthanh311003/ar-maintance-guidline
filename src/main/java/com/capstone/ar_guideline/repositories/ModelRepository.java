package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, String> {}
