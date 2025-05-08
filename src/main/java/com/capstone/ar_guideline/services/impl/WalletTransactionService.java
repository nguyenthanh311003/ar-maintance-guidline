package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.Wallet.WalletTransactionResponse;
import com.capstone.ar_guideline.entities.WalletTransaction;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.WalletTransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionService {

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  public PagingModel<WalletTransactionResponse> getAllByUserId(
      int page, int size, String userId, String type, String serviceName, String receiverName) {
    try {
      PagingModel<WalletTransactionResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);

      Page<WalletTransaction> transactions =
          walletTransactionRepository.findByUserId(
              pageable, userId, type, serviceName, receiverName);

      List<WalletTransactionResponse> walletTransactionResponses =
          transactions.getContent().stream()
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
                          .senderName(
                              transaction.getSender() != null
                                  ? transaction.getSender().getUsername()
                                  : null)
                          .receiverName(
                              transaction.getReceiver() != null
                                  ? transaction.getReceiver().getUsername()
                                  : null)
                          .optionName(
                              transaction.getPointOptions() != null
                                  ? transaction.getPointOptions().getName()
                                  : null)
                          .modelRequestId(
                              transaction.getRequestRevision() != null
                                  ? transaction
                                      .getRequestRevision()
                                      .getCompanyRequest()
                                      .getRequestNumber()
                                  : null)
                          .revisionType(
                              transaction.getRequestRevision() != null
                                  ? transaction.getRequestRevision().getType()
                                  : null)
                          .createdDate(transaction.getCreatedDate())
                          .build())
              .toList();

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) transactions.getTotalElements());
      pagingModel.setTotalPages(transactions.getTotalPages());
      pagingModel.setObjectList(walletTransactionResponses);
      return pagingModel;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }
}
