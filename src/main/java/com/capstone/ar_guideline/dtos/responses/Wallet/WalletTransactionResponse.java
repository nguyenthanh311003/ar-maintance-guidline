package com.capstone.ar_guideline.dtos.responses.Wallet;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionResponse {
  private String id;

  private String type;

  private String serviceName;

  private String guidelineName;

  private String optionName;

  private Long amount;

  private Long balance;

  private LocalDateTime createdDate;
}
