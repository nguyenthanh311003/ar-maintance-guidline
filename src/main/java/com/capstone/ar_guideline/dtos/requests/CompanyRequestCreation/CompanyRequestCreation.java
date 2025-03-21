package com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestCreation {
  private String requestId;
  private String requestSubject;
  private String requestDescription;
  private String companyId;
  private String machineId;
  private String designerId;
  private String requesterId;
  private String assetModelId;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
  private LocalDateTime cancelledAt;
}
