package com.capstone.ar_guideline.payos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
  private String userId;
  private String productName;
}
