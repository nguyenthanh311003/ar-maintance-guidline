package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.WalletTransaction;
import com.capstone.ar_guideline.repositories.WalletTransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionService {

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  public List<WalletTransaction> getAllByUserId(String userId) {
    return walletTransactionRepository.findAllByWalletUserId(userId);
  }
}
