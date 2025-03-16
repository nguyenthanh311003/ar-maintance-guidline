package com.capstone.ar_guideline.dtos.responses.CompanyRequest;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestResponse {
  private String requestId;
  private CompanyResponse company;
  private UserResponse designer;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
  private LocalDateTime cancelledAt;
}
