package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.WalletTransaction;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
  List<WalletTransaction> findAllByWalletUserIdOrderByCreatedDateDesc(String userId);

  @Query("SELECT CASE WHEN COUNT(wt) > 0 THEN TRUE ELSE FALSE END FROM WalletTransaction wt WHERE wt.course.id = :guidelineId")
  Boolean isGuidelinePay(@Param("guidelineId") String guidelineId);

  @Query(
          value = "SELECT m.month, COALESCE(COUNT(wt.id), 0) AS transactionCount " +
                  "FROM ( " +
                  "   SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
                  "   UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 " +
                  "   UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 " +
                  ") AS m " +
                  "LEFT JOIN wallet_transaction wt ON m.month = MONTH(wt.created_date) " +
                  "AND wt.created_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                  "LEFT JOIN service_price sp ON wt.service_price_id = sp.id " +
                  "WHERE sp.name = 'Point Request' " +
                  "GROUP BY m.month " +
                  "ORDER BY m.month",
          nativeQuery = true
  )
  List<Object[]> findPointRequestTransactionsOverLast12Months();

  @Query(
          "SELECT u.username, COUNT(wt.id) AS transactionCount " +
                  "FROM WalletTransaction wt " +
                  "JOIN wt.user u " +
                  "JOIN wt.servicePrice sp " +
                  "WHERE sp.name = 'Point Request' AND u.role.roleName ='STAFF' "  +
                  "GROUP BY u.username " +
                  "ORDER BY transactionCount DESC"
  )
  List<Object[]> findTop3UsersWithPointRequestTransactions(Pageable pageable);
}


