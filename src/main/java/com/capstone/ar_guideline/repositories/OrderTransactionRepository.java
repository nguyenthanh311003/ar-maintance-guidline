package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.OrderTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, String> {
  @Query(
      value =
          "SELECT o FROM OrderTransaction o WHERE o.user.company.id = :companyId "
              + "AND o.user.role.roleName = 'COMPANY' "
              + "ORDER BY o.createdDate ASC")
  Page<OrderTransaction> getOrderTransactionByCompanyId(
      Pageable pageable, @Param("companyId") String companyId);

  OrderTransaction findByOrderCode(Long orderCode);
}
