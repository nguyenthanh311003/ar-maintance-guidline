package com.capstone.ar_guideline.dtos.responses.OrderTransaction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTransactionResponse {
  String id;
  String userId;
  String itemCode;
  String orderCode;
  String status;
  Double amount;
}
