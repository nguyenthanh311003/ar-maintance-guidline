package com.capstone.ar_guideline.services.impl;

import static com.capstone.ar_guideline.constants.ConstAPI.OrderTransactionAPI.HANDLE_ORDER_STATUS;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.entities.OrderTransaction;
import com.capstone.ar_guideline.entities.PointOptions;
import com.capstone.ar_guideline.payos.CreatePaymentLinkRequestBody;
import com.capstone.ar_guideline.services.IOrderTransactionService;
import com.capstone.ar_guideline.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

@Service
public class PayOsService {

  @Autowired PayOS payOS;

  @Value("${application.url}")
  private String backEndHost;

  @Value("${frontend.url}")
  private String frontEndHost;

  @Autowired private IOrderTransactionService paymentService;

  @Autowired WalletServiceImpl walletService;

  @Autowired private PointOptionsService pointOptionsService;

  @Autowired IUserService userService;

  public ObjectNode createPaymentLink(CreatePaymentLinkRequestBody requestBody) {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    try {

      String returnUrl = backEndHost + "/" + HANDLE_ORDER_STATUS;
      String cancelUrl = "";
      PointOptions pointOptions = pointOptionsService.findById(requestBody.getPointOptionsId());
      // create order
      OrderTransactionCreationRequest paymentRequest = new OrderTransactionCreationRequest();
      paymentRequest.setUserId(requestBody.getUserId());
      paymentRequest.setAmount(pointOptions.getAmount());
      paymentRequest.setPoint(pointOptions.getPoint());
      paymentRequest.setPointOptionsId(pointOptions.getId());
      OrderTransactionResponse payment = paymentService.create(paymentRequest);

      // Gen order code
      String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
      Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
      paymentService.UpdateOrderCode(payment.getId(), orderCode);

      returnUrl = returnUrl + orderCode;
      cancelUrl = returnUrl;
      ItemData item =
          ItemData.builder()
              .name("point")
              .price(Math.toIntExact(pointOptions.getAmount()))
              .quantity(1)
              .build();

      PaymentData paymentData =
          PaymentData.builder()
              .orderCode(orderCode)
              .description(pointOptions.getName())
              .amount(Math.toIntExact(pointOptions.getAmount()))
              .item(item)
              .returnUrl(returnUrl)
              .cancelUrl(cancelUrl)
              .build();

      CheckoutResponseData data = payOS.createPaymentLink(paymentData);

      response.put("error", 0);
      response.put("message", "success");
      response.set("data", objectMapper.valueToTree(data));
      return response;

    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", "fail");
      response.set("data", null);
      return response;
    }
  }

  public ObjectNode getOrderById(long orderId) {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();

    try {
      PaymentLinkData order = null;
      order = payOS.getPaymentLinkInformation(orderId);

      response.set("data", objectMapper.valueToTree(order));
      response.put("error", 0);
      response.put("message", "ok");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  public ObjectNode cancelOrder(int orderId) {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    try {
      PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);
      response.set("data", objectMapper.valueToTree(order));
      response.put("error", 0);
      response.put("message", "ok");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  public ObjectNode confirmWebhook(Map<String, String> requestBody) {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    try {
      String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
      response.set("data", objectMapper.valueToTree(str));
      response.put("error", 0);
      response.put("message", "ok");
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  public RedirectView handleOrderStatus(long orderId) {
    try {
      PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);
      OrderTransaction payment = paymentService.findByOrderCode(orderId);
      if (order.getStatus().equals(ConstStatus.PAID)
          && !payment.getStatus().equals(ConstStatus.PAID)) {
        paymentService.changeStatus(payment.getId(), ConstStatus.PAID);
        // Update wallet balance
        walletService.updateBalance(
            payment.getUser().getWallet().getId(),
            (long) (payment.getAmount() / 1000),
            true,
            null,
            payment.getUser().getId(),
            null);
      } else if (!order.getStatus().equals(ConstStatus.PAID)) {
        paymentService.changeStatus(payment.getId(), ConstStatus.CANCEL);
        return new RedirectView(frontEndHost + "/payment/failed");
      }
      return new RedirectView(frontEndHost + "/payment/success");
    } catch (Exception e) {
      e.printStackTrace();
      return new RedirectView(frontEndHost + "/payment/failed");
    }
  }
}
