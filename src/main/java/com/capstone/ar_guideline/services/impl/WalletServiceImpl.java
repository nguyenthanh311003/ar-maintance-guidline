package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.entities.Wallet;
import com.capstone.ar_guideline.mappers.WalletMapper;
import com.capstone.ar_guideline.repositories.WalletRepository;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class WalletServiceImpl {

  @Autowired private WalletRepository walletRepository;

  @Transactional
  public WalletResponse createWallet(User user, Long initialBalance, String currency) {
    Wallet wallet = Wallet.builder().user(user).balance(initialBalance).currency(currency).build();
    log.info("Creating wallet for user: {}", user.getId());
    log.info("Wallet details: {}", wallet);

    Wallet savedWallet = walletRepository.save(wallet);
    log.info("Wallet saved with ID: {}", savedWallet.getId());
    return WalletMapper.toResponse(walletRepository.save(wallet));
  }

  public Wallet updateBalance(String walletId, Long amount, boolean isPlus) {
    Optional<Wallet> walletOptional = walletRepository.findById(walletId);
    if (walletOptional.isPresent()) {
      Wallet wallet = walletOptional.get();
      if (isPlus) {
        wallet.setBalance(wallet.getBalance() + (amount));
      } else {
        wallet.setBalance(wallet.getBalance() - (amount));
      }
      return walletRepository.save(wallet);
    } else {
      throw new RuntimeException("Wallet not found");
    }
  }

  public WalletResponse findWalletByUserId(String userId) {
    return WalletMapper.toResponse(walletRepository.findByUserId(userId).get());
  }
}
