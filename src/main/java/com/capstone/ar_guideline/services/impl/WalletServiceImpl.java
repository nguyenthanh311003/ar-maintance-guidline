package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.mappers.WalletMapper;
import com.capstone.ar_guideline.repositories.*;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletServiceImpl {

  @Autowired private WalletRepository walletRepository;

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CompanyRepository companyRepository;

  @Autowired private PointOptionsRepository pointOptionsRepository;

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
        PointOptions pointOptions = pointOptionsRepository.findById(pointOptionsId).get();
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

public Wallet updateBalanceBySend(
    Long amount,
    String receiverId,
    String senderId
) {
    Optional<Wallet> walletReceiverOptional = walletRepository.findByUserId(receiverId);
    Optional<Wallet> walletSenderOptional = walletRepository.findByUserId(senderId);

    if (walletSenderOptional.isPresent() && walletReceiverOptional.isPresent()) {
        Wallet senderWallet = walletSenderOptional.get();
        Wallet receiverWallet = walletReceiverOptional.get();

        if (senderWallet.getBalance() == 0 || senderWallet.getBalance() - amount < 0) {
            throw new RuntimeException("Sender wallet does not have enough balance");
        }

        // Subtract the amount from the sender's wallet
        Long newSenderBalance = senderWallet.getBalance() - amount;
        senderWallet.setBalance(newSenderBalance);

        // Add the amount to the receiver's wallet
        Long newReceiverBalance = receiverWallet.getBalance() + amount;
        receiverWallet.setBalance(newReceiverBalance);

        // Create a WalletTransaction for the sender
        WalletTransaction senderTransaction = WalletTransaction.builder()
            .wallet(senderWallet)
            .amount(amount)
            .balance(newSenderBalance)
            .type("DEBIT")
            .user(User.builder().id(senderId).build())
            .receiver(User.builder().id(receiverId).build())
            .build();
        walletTransactionRepository.save(senderTransaction);

        // Create a WalletTransaction for the receiver
        WalletTransaction receiverTransaction = WalletTransaction.builder()
            .wallet(receiverWallet)
            .amount(amount)
            .balance(newReceiverBalance)
            .type("CREDIT")
            .user(User.builder().id(receiverId).build())
            .sender(User.builder().id(senderId).build())
            .build();
        walletTransactionRepository.save(receiverTransaction);

        // Save the updated wallets
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        return receiverWallet;
    } else {
        throw new RuntimeException("Sender or receiver wallet not found");
    }
}

  public WalletResponse findWalletByUserId(String userId) {
    return WalletMapper.toResponse(walletRepository.findByUserId(userId).get());
  }

public void updatePointForAllEmployeeByCompanyId(String companyId, Long limitPoint) {
  // Find all users by companyId
  List<User> users = userRepository.findByCompanyId(companyId);
  User company = userRepository.findUserByCompanyIdAndAdminRole(companyId);
  Long totalOfPoints = 0L;
    for (User user : users) {
        if (!user.getRole().getRoleName().equals("COMPANY") && user.getWallet().getBalance() < limitPoint) {
            // Calculate the gap to the limit point
            Long gap = limitPoint - user.getWallet().getBalance();
            totalOfPoints = totalOfPoints + gap;
        }
    }

    if(totalOfPoints > company.getWallet().getBalance()){
        throw new RuntimeException("Company wallet does not have enough balance");
    }
    // Distribute points to other users
  for (User user : users) {
    if (!user.getRole().getRoleName().equals("COMPANY") && user.getWallet().getBalance() < limitPoint) {
      // Calculate the gap to the limit point
      Long gap = limitPoint - user.getWallet().getBalance();

      // Update the wallet balance
      updateBalanceBySend(gap, user.getId(), company.getId());
    }
  }

}
}
