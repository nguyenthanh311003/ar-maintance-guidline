package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.mappers.WalletMapper;
import com.capstone.ar_guideline.repositories.WalletRepository;
import com.capstone.ar_guideline.repositories.WalletTransactionRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletServiceImpl {

  @Autowired private WalletRepository walletRepository;

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  public WalletResponse createWallet(User user, Long initialBalance, String currency) {
    Wallet wallet = Wallet.builder().user(user).balance(initialBalance).currency(currency).build();
    log.info("Creating wallet for user: {}", user.getId());
    log.info("Wallet details: {}", wallet);

    Wallet savedWallet = walletRepository.save(wallet);
    log.info("Wallet saved with ID: {}", savedWallet.getId());
    return WalletMapper.toResponse(walletRepository.save(wallet));
  }

  public Wallet updateBalance(
      String walletId,
      Long amount,
      boolean isPlus,
      String servicePriceId,
      String userId,
      String guidelineId,
      String pointOptionsId) {
    Optional<Wallet> walletOptional = walletRepository.findById(walletId);
    if ((walletOptional.get().getBalance() == 0 || walletOptional.get().getBalance() - amount < 0)
        && !isPlus) {
      throw new RuntimeException("Wallet not have enough balance");
    }
    if (walletOptional.isPresent()) {
      Wallet wallet = walletOptional.get();
      Long newBalance = isPlus ? wallet.getBalance() + amount : wallet.getBalance() - amount;
      wallet.setBalance(newBalance);
      WalletTransaction transaction =
          WalletTransaction.builder()
              .wallet(wallet)
              .amount(amount)
              .balance(newBalance)
              .type(isPlus ? "CREDIT" : "DEBIT")
              .user(User.builder().id(userId).build())
              .build();

      if (pointOptionsId != null) {
        PointOptions pointOptions = PointOptions.builder().id(pointOptionsId).build();
        transaction.setPointOptions(pointOptions);
        transaction.setAmount(pointOptions.getPoint());
        transaction.setBalance(wallet.getBalance() + pointOptions.getPoint());
      } else {
        transaction.setServicePrice(ServicePrice.builder().id(servicePriceId).build());
        transaction.setCourse(Course.builder().id(guidelineId).build());
      }
      walletTransactionRepository.save(transaction);

      return walletRepository.save(wallet);
    } else {
      throw new RuntimeException("Wallet not found");
    }
  }

  public WalletResponse findWalletByUserId(String userId) {
    return WalletMapper.toResponse(walletRepository.findByUserId(userId).get());
  }
}
