package com.capstone.ar_guideline.payos;

import com.capstone.ar_guideline.constants.ConstStatus;
import java.util.UUID;
import lombok.Data;

@Data
public class PaymentRequest {
  private UUID userId;
  private int total;
  private String subscriptionCode;
  private String method;
  private String status = ConstStatus.PENDING;

  public String vnp_OrderInfo = "Subscription";
  public String vnp_OrderType = "200000";
  public String vnp_TxnRef;
}
