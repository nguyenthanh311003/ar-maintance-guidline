package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.OrderTransaction;

public interface IOrderTransactionService {
  OrderTransactionResponse create(OrderTransactionCreationRequest request);

  OrderTransactionResponse update(String id, OrderTransactionCreationRequest request);

  void delete(String id);

  OrderTransaction findById(String id);

  void UpdateOrderCode(String orderId, Long orderCode);

  PagingModel<OrderTransactionResponse> getAllTransactionByCompanyId(
      int page, int size, String companyId);

  OrderTransaction findByOrderCode(Long orderCode);

  void changeStatus(String orderId, String status);
}
