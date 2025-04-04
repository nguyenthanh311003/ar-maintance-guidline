package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.PointRequestResponse.PointRequestCreation;
import com.capstone.ar_guideline.dtos.responses.PointRequestResponse.PointRequestResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.PointRequest;
import com.capstone.ar_guideline.entities.User;

public class PointRequestMapper {
  public static PointRequest fromPointRequestCreationRequestToEntity(PointRequestCreation request) {
    return PointRequest.builder()
        .reason(request.getReason())
        .amount(request.getAmount())
        .company(Company.builder().id(request.getCompanyId()).build())
        .employee(User.builder().id(request.getEmployeeId()).build())
        .status(request.getStatus())
        .requestNumber(request.getRequestNumber())
        .build();
  }

  public static PointRequestResponse fromEntityToPointRequestResponse(PointRequest pointRequest) {
    return PointRequestResponse.builder()
        .id(pointRequest.getId())
        .reason(pointRequest.getReason())
        .company(
            pointRequest.getCompany() == null
                ? null
                : CompanyMapper.fromEntityToCompanyResponse(pointRequest.getCompany()))
        .employee(
            pointRequest.getEmployee() == null
                ? null
                : UserMapper.fromEntityToUserResponse(pointRequest.getEmployee()))
        .amount(pointRequest.getAmount())
        .status(pointRequest.getStatus())
        .requestNumber(pointRequest.getRequestNumber())
        .createdAt(pointRequest.getCreatedAt().toString())
        .completedAt(
            pointRequest.getCompletedAt() != null ? pointRequest.getCompletedAt().toString() : null)
        .build();
  }
}
