package com.capstone.ar_guideline.dtos.responses.CompanyRequest;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
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
  private String requestSubject;
  private String requestDescription;
  private CompanyResponse company;
  private UserResponse designer;
  private UserResponse requester;
  private MachineResponse machine;
  private String status;
  private ModelResponse assetModel;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
  private LocalDateTime cancelledAt;
}
