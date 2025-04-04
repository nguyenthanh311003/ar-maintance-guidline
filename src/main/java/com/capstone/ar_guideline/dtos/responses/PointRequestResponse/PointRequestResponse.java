package com.capstone.ar_guideline.dtos.responses.PointRequestResponse;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointRequestResponse {
  String id;
  String reason;
  Long amount;
  CompanyResponse company;
  UserResponse employee;
  String status;
  String requestNumber;
  String createdAt;
  String completedAt;
}
