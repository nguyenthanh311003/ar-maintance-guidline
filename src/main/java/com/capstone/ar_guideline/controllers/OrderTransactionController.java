package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.payos.CreatePaymentLinkRequestBody;
import com.capstone.ar_guideline.services.IOrderTransactionService;
import com.capstone.ar_guideline.services.impl.PayOsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderTransactionController {
  IOrderTransactionService orderTransactionService;

  @Autowired PayOS payOS;

  @Autowired PayOsService payOsService;

  @PostMapping(ConstAPI.OrderTransactionAPI.CREATE_ORDER_TRANSACTION)
  public ObjectNode createPaymentLink(@RequestBody CreatePaymentLinkRequestBody RequestBody) {
    return payOsService.createPaymentLink(RequestBody);
  }

  @GetMapping(ConstAPI.OrderTransactionAPI.HANDLE_ORDER_STATUS + "{orderId}")
  public RedirectView createPaymentLink(@PathVariable Long orderId) {
    return payOsService.handleOrderStatus(orderId);
  }

  @PostMapping(path = "/payment/payos_transfer_handler")
  public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
      throws JsonProcessingException, IllegalArgumentException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);

    try {
      // Init Response
      response.put("error", 0);
      response.put("message", "Webhook delivered");
      response.set("data", null);

      WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);
      System.out.println(data);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  @GetMapping(
      value = ConstAPI.OrderTransactionAPI.GET_ORDER_TRANSACTION_BY_COMPANY_ID + "{companyId}")
  ApiResponse<PagingModel<OrderTransactionResponse>> getOrderTransactionByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) Long orderCode,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<OrderTransactionResponse>>builder()
        .result(orderTransactionService.getAllTransactionByCompanyId(page, size, companyId, status, orderCode))
        .build();
  }

  @GetMapping(value = ConstAPI.OrderTransactionAPI.GET_ALL_ORDER_TRANSACTION)
  ApiResponse<PagingModel<OrderTransactionResponse>> getOrderTransaction(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
    return ApiResponse.<PagingModel<OrderTransactionResponse>>builder()
        .result(orderTransactionService.getAllTransaction(page, size))
        .build();
  }

  //  @PostMapping(value = ConstAPI.OrderTransactionAPI.CREATE_ORDER_TRANSACTION)
  //  ApiResponse<OrderTransactionResponse> createOrderTransaction(
  //      @RequestBody @Valid OrderTransactionCreationRequest request) {
  //    return ApiResponse.<OrderTransactionResponse>builder()
  //        .result(orderTransactionService.create(request))
  //        .build();
  //  }

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
