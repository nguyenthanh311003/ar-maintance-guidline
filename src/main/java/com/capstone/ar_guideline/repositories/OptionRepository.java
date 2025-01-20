package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, String> {}
