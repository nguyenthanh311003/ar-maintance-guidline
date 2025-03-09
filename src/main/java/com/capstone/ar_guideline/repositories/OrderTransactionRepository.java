package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.OrderTransaction;
import java.util.List;
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
              + "ORDER BY o.createdDate DESC")
  Page<OrderTransaction> getOrderTransactionByCompanyId(
      Pageable pageable, @Param("companyId") String companyId);

  OrderTransaction findByOrderCode(Long orderCode);

  @Query(value = "SELECT o FROM OrderTransaction o " + "ORDER BY o.createdDate DESC")
  Page<OrderTransaction> getOrderTransaction(Pageable pageable);

  @Query(
      "SELECT c, COALESCE(SUM(ot.amount), 0) AS totalAmount "
          + "FROM Company c "
          + "LEFT JOIN c.users u "
          + "LEFT JOIN u.orderTransactions ot "
          + "WHERE ot.status = 'PAID' "
          + "GROUP BY c "
          + "ORDER BY totalAmount DESC")
  List<Object[]> getCompaniesWithTotalPaidOrders();

  @Query(
      "SELECT s, COALESCE(SUM(ot.amount), 0) AS totalAmount "
          + "FROM Subscription s "
          + "JOIN s.companySubscriptions cs "
          + "JOIN cs.company c "
          + "JOIN c.users u "
          + "JOIN u.orderTransactions ot "
          + "WHERE ot.status = 'PAID' "
          + "GROUP BY s "
          + "ORDER BY totalAmount DESC")
  List<Object[]> getSubcriptionWithTotalPaidOrders();
}
