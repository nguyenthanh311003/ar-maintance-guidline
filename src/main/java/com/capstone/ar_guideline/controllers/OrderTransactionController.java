package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IOrderTransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderTransactionController {
  IOrderTransactionService orderTransactionService;

  @GetMapping(
      value = ConstAPI.OrderTransactionAPI.GET_ORDER_TRANSACTION_BY_COMPANY_ID + "{companyId}")
  ApiResponse<PagingModel<OrderTransactionResponse>> getOrderTransactionByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<OrderTransactionResponse>>builder()
        .result(orderTransactionService.getAllTransactionByCompanyId(page, size, companyId))
        .build();
  }

  @PostMapping(value = ConstAPI.OrderTransactionAPI.CREATE_ORDER_TRANSACTION)
  ApiResponse<OrderTransactionResponse> createOrderTransaction(
      @RequestBody @Valid OrderTransactionCreationRequest request) {
    return ApiResponse.<OrderTransactionResponse>builder()
        .result(orderTransactionService.create(request))
        .build();
  }

  @PutMapping(
      value = ConstAPI.OrderTransactionAPI.UPDATE_ORDER_TRANSACTION + "{orderTransactionId}")
  ApiResponse<OrderTransactionResponse> updateOrderTransaction(
      @PathVariable String orderTransactionId,
      @RequestBody OrderTransactionCreationRequest request) {
    return ApiResponse.<OrderTransactionResponse>builder()
        .result(orderTransactionService.update(orderTransactionId, request))
        .build();
  }

  @DeleteMapping(
      value = ConstAPI.OrderTransactionAPI.DELETE_ORDER_TRANSACTION + "{orderTransactionId}")
  ApiResponse<String> deleteOrderTransaction(@PathVariable String orderTransactionId) {
    orderTransactionService.delete(orderTransactionId);
    return ApiResponse.<String>builder().result("Order transaction has been deleted").build();
  }
}
