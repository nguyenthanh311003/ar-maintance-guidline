package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.WalletTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
  List<WalletTransaction> findAllByWalletUserIdOrderByCreatedDateDesc(String userId);
}
