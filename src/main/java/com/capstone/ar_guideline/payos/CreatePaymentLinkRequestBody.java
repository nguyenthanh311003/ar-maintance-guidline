package com.capstone.ar_guideline.payos;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreatePaymentLinkRequestBody {
  private String userId;
  private Integer amount;
}
