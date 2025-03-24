package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.PointOptions;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointOptionsRepository extends JpaRepository<PointOptions, String> {
  @EntityGraph(attributePaths = "orderTransactions")
  List<PointOptions> findAllByOrderByPointAsc();

  @Query(
      "SELECT po.name, COALESCE(SUM(ot.amount), 0) FROM PointOptions po "
          + "left JOIN po.orderTransactions ot "
          + "ON ot.status = 'PAID' "
          + "GROUP BY po.name")
  List<Object[]> findAllOptionNamesWithTotalAmount();
}
