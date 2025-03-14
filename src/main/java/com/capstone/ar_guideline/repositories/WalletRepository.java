package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
  Optional<Wallet> findByUserId(String userId);
}
