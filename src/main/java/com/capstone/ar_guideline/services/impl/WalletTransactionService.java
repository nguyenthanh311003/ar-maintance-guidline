package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Wallet.WalletTransactionResponse;
import com.capstone.ar_guideline.entities.WalletTransaction;
import com.capstone.ar_guideline.repositories.WalletTransactionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionService {

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  public List<WalletTransactionResponse> getAllByUserId(String userId) {
    List<WalletTransaction> transactions =
        walletTransactionRepository.findAllByWalletUserId(userId);
    return transactions.stream()
        .map(
            transaction ->
                WalletTransactionResponse.builder()
                    .id(transaction.getId())
                    .type(transaction.getType())
                        .serviceName(
                                transaction.getServicePrice() != null
                                        ? transaction.getServicePrice().getName()
                                        : null)
                        .guidelineName(
                                transaction.getCourse() != null
                                        ? transaction.getCourse().getTitle()
                                        : null)
                    .amount(transaction.getAmount())
                    .balance(transaction.getBalance())
                        .optionName(
                            transaction.getPointOptions() != null
                                ? transaction.getPointOptions().getName()
                                : null)
                    .createdDate(transaction.getCreatedDate())
                    .build())
        .collect(Collectors.toList());
  }
}
