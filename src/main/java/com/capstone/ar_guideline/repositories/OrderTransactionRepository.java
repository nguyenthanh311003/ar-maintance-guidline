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
              + "AND ((:status IS NULL OR LOWER(o.status) LIKE LOWER(CONCAT('%', :status, '%')))) "
              + "AND (:orderCode IS NULL OR STR(o.orderCode) LIKE CONCAT('%', :orderCode, '%')) "
              + "ORDER BY o.createdDate DESC")
  Page<OrderTransaction> getOrderTransactionByCompanyId(
      Pageable pageable,
      @Param("companyId") String companyId,
      @Param("status") String status,
      @Param("orderCode") String orderCode);

  OrderTransaction findByOrderCode(Long orderCode);

  @Query(
      value =
          "SELECT o FROM OrderTransaction o "
              + "WHERE ((:status IS NULL OR LOWER(o.status) LIKE LOWER(CONCAT('%', :status, '%')))) "
              + "AND (:orderCode IS NULL OR STR(o.orderCode) LIKE CONCAT('%', :orderCode, '%')) "
              + "ORDER BY o.createdDate DESC")
  Page<OrderTransaction> getOrderTransaction(Pageable pageable, String status, String orderCode);

  @Query(
      "SELECT c, COALESCE(SUM(ot.amount), 0) AS totalAmount "
          + "FROM Company c "
          + "LEFT JOIN c.users u "
          + "LEFT JOIN u.orderTransactions ot "
          + "WHERE ot.status = 'PAID' "
          + "GROUP BY c "
          + "ORDER BY totalAmount DESC")
  List<Object[]> getCompaniesWithTotalPaidOrders(Pageable pageable);

  @Query(
      value =
          "SELECT m.month, COALESCE(SUM(ot.amount), 0) AS totalAmount "
              + "FROM (SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) AS m "
              + "LEFT JOIN order_transaction ot ON m.month = MONTH(ot.created_date) AND YEAR(ot.created_date) = :year AND ot.status = 'PAID' "
              + "GROUP BY m.month "
              + "ORDER BY m.month",
      nativeQuery = true)
  List<Object[]> getMonthlyPaidOrderAmounts(@Param("year") int year);
}
