package com.capstone.ar_guideline.dtos.requests.OrderTransaction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTransactionCreationRequest {
  String userId;
  String pointOptionsId;
  Long amount;
  Long point;
}
