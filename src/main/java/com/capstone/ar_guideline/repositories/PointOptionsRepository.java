package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.PointOptions;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointOptionsRepository extends JpaRepository<PointOptions, String> {
  @EntityGraph(attributePaths = "orderTransactions")
  List<PointOptions> findAllByOrderByPointAsc();
}
