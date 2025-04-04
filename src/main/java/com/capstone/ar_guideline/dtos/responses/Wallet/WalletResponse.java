package com.capstone.ar_guideline.dtos.responses.Wallet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

  private String id;

  private String userId;

  private Long balance;

  private String currency;
}
