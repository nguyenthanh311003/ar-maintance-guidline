package com.capstone.ar_guideline.dtos.responses.CompanyRequest;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestResponse implements Serializable {
  private String requestId;
  private String requestSubject;
  private String requestDescription;
  private CompanyResponse company;
  private UserResponse designer;
  private UserResponse requester;
  private MachineTypeResponse machineType;
  private String requestNumber;
  private String cancelReason;
  private UserResponse cancelledBy;
  private String status;
  private ModelResponse assetModel;
  private LocalDateTime createdAt;
  private LocalDateTime completedAt;
  private LocalDateTime cancelledAt;
}
