package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.OrderTransaction;
import com.capstone.ar_guideline.entities.PointOptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointOptionsRepository extends JpaRepository<PointOptions, String> {

}
