package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.entities.OrderTransaction;
import com.capstone.ar_guideline.entities.User;

public class OrderTransactionMapper {
  public static OrderTransaction fromOrderTransactionCreationRequestToEntity(
      OrderTransactionCreationRequest request, User user) {
    return OrderTransaction.builder()
        .user(user)
        .amount(Double.valueOf(request.getAmount()))
        .build();
  }

  public static OrderTransactionResponse fromEntityToOrderTransactionResponse(
      OrderTransaction orderTransaction) {
    return OrderTransactionResponse.builder()
        .id(orderTransaction.getId())
        .userId(orderTransaction.getUser().getId())
        .orderCode(orderTransaction.getOrderCode())
        .status(orderTransaction.getStatus())
        .amount(orderTransaction.getAmount())
        .createdDate(orderTransaction.getCreatedDate())
        .updatedDate(orderTransaction.getUpdatedDate())
        .build();
  }
}
