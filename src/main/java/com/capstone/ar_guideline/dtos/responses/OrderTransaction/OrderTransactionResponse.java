package com.capstone.ar_guideline.dtos.responses.OrderTransaction;

import java.time.LocalDateTime;
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
  Long orderCode;
  String status;
  Long amount;
  Long point;
  String optionName;
  String email;
  LocalDateTime createdDate;
  LocalDateTime updatedDate;
}
