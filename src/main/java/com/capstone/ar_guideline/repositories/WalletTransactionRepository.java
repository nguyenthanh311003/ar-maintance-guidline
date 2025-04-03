package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.WalletTransaction;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
  @Query(
      value =
          "SELECT wt FROM WalletTransaction wt "
              + "LEFT JOIN wt.servicePrice sp "
              + "LEFT JOIN wt.receiver r "
              + "WHERE wt.user.id = :userId "
              + "AND (:type IS NULL OR wt.type = :type) "
              + "AND (:serviceName IS NULL OR sp.name = :serviceName) "
              + "AND (:receiverName IS NULL OR LOWER(r.username) LIKE LOWER(CONCAT('%', :receiverName, '%'))) "
              + "ORDER BY wt.createdDate DESC")
  Page<WalletTransaction> findByUserId(
      Pageable pageable,
      @Param("userId") String userId,
      @Param("type") String type,
      @Param("serviceName") String serviceName,
      @Param("receiverName") String receiverName);

  @Query(
      "SELECT CASE WHEN COUNT(wt) > 0 THEN TRUE ELSE FALSE END FROM WalletTransaction wt WHERE wt.course.id = :guidelineId")
  Boolean isGuidelinePay(@Param("guidelineId") String guidelineId);

  @Query(
      value =
          "SELECT m.month, COALESCE(COUNT(wt.id), 0) AS transactionCount "
              + "FROM ( "
              + "   SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 "
              + "   UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 "
              + "   UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 "
              + ") AS m "
              + "LEFT JOIN wallet_transaction wt ON m.month = MONTH(wt.created_date) "
              + "AND wt.created_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) "
              + "LEFT JOIN service_price sp ON wt.service_price_id = sp.id "
              + "LEFT JOIN user u ON wt.user_id = u.id "
              + "WHERE sp.name = 'Point Request' AND u.company_id = :companyId "
              + "GROUP BY m.month "
              + "ORDER BY m.month",
      nativeQuery = true)
  List<Object[]> findPointRequestTransactionsOverLast12Months(@Param("companyId") String companyId);

  @Query(
      "SELECT u.username, COUNT(wt.id) AS transactionCount "
          + "FROM WalletTransaction wt "
          + "JOIN wt.user u "
          + "JOIN wt.servicePrice sp "
          + "WHERE sp.name = 'Point Request' AND u.role.roleName = 'STAFF' AND u.company.id = :companyId "
          + "GROUP BY u.username "
          + "ORDER BY transactionCount DESC")
  List<Object[]> findTop3UsersWithPointRequestTransactions(
      Pageable pageable, @Param("companyId") String companyId);
}
