package com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation;

import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRequestCreation {
  String requestId;
  String requestSubject;
  String requestDescription;
  String companyId;
  String machineTypeId;
  String designerId;
  String requesterId;
  String assetModelId;
  String status;
  RequestRevisionRequest requestRevision;
  LocalDateTime createdAt;
  LocalDateTime completedAt;
  LocalDateTime cancelledAt;
}
