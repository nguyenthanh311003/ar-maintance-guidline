package com.capstone.ar_guideline.payos;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreatePaymentLinkRequestBody {
  private String userId;
  private String productName;
}
