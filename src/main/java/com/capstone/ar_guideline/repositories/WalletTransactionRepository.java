package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.WalletTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
  List<WalletTransaction> findAllByWalletUserIdOrderByCreatedDateDesc(String userId);

  @Query("SELECT CASE WHEN COUNT(wt) > 0 THEN TRUE ELSE FALSE END FROM WalletTransaction wt WHERE wt.course.id = :guidelineId")
  Boolean isGuidelinePay(@Param("guidelineId") String guidelineId);
}
