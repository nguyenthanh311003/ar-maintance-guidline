package com.capstone.ar_guideline.dtos.requests.PointRequestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointRequestCreation {
  String id;
  String reason;
  Long amount;
  String companyId;
  String employeeId;
  String status;
  String requestNumber;
  String createdAt;
  String completedAt;
}
