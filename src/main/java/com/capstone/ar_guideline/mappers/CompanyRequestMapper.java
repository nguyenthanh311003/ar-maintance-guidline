package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.entities.CompanyRequest;

public class CompanyRequestMapper {
  public static CompanyRequest fromCreationRequestToEntity(CompanyRequestCreation request) {
    return CompanyRequest.builder()
        .requestId(request.getRequestId())
        .status(request.getStatus())
        .completedAt(request.getCompletedAt())
        .cancelledAt(request.getCancelledAt())
        .build();
  }

  public static CompanyRequestResponse fromEntityToResponse(CompanyRequest entity) {
    return CompanyRequestResponse.builder()
        .requestId(entity.getRequestId())
        .company(CompanyMapper.fromEntityToCompanyResponse(entity.getCompany()))
        .designer(UserMapper.fromEntityToUserResponse(entity.getDesigner()))
        .completedAt(entity.getCompletedAt())
        .cancelledAt(entity.getCancelledAt())
        .status(entity.getStatus())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public static CompanyRequest fromResponseToEntity(CompanyRequestResponse response) {
    return CompanyRequest.builder()
        .requestId(response.getRequestId())
        .company(CompanyMapper.fromCompanyResponseToEntity(response.getCompany()))
        .designer(UserMapper.fromUserResponseToEntity(response.getDesigner()))
        .completedAt(response.getCompletedAt())
        .cancelledAt(response.getCancelledAt())
        .status(response.getStatus())
        .createdAt(response.getCreatedAt())
        .build();
  }
}
