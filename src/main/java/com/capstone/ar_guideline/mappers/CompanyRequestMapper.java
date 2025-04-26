package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.entities.CompanyRequest;
import java.util.stream.Collectors;

public class CompanyRequestMapper {
  public static CompanyRequest fromCreationRequestToEntity(CompanyRequestCreation request) {
    return CompanyRequest.builder()
        .requestId(request.getRequestId())
        .requestSubject(request.getRequestSubject())
        .requestDescription(request.getRequestDescription())
        .status(request.getStatus())
        .completedAt(request.getCompletedAt())
        .cancelledAt(request.getCancelledAt())
        .build();
  }

  public static CompanyRequestResponse fromEntityToResponse(CompanyRequest entity) {
    return CompanyRequestResponse.builder()
        .requestId(entity.getRequestId())
        .requestSubject(entity.getRequestSubject())
        .requestDescription(entity.getRequestDescription())
        .company(
            entity.getCompany() == null
                ? null
                : CompanyMapper.fromEntityToCompanyResponse(entity.getCompany()))
        .designer(
            entity.getDesigner() == null
                ? null
                : UserMapper.fromEntityToUserResponse(entity.getDesigner()))
        .cancelledBy(
            entity.getCancelledBy() == null
                ? null
                : UserMapper.fromEntityToUserResponse(entity.getCancelledBy()))
        .machineType(
            entity.getMachineType() == null
                ? null
                : MachineTypeMapper.fromEntityToMachineTypeResponse(
                    entity.getMachineType(),
                    entity.getMachineType().getMachineTypeAttributes().stream()
                        .map(MachineTypeAttributeMapper::fromEntityToMachineTypeAttributeResponse)
                        .collect(Collectors.toList())))
        .assetModel(
            entity.getAssetModel() == null
                ? null
                : ModelMapper.fromEntityToModelResponse(entity.getAssetModel()))
        .requester(
            entity.getRequester() == null
                ? null
                : UserMapper.fromEntityToUserResponse(entity.getRequester()))
        .requestNumber(entity.getRequestNumber())
        .cancelReason(entity.getCancelReason())
        .completedAt(entity.getCompletedAt())
        .cancelledAt(entity.getCancelledAt())
        .status(entity.getStatus())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
