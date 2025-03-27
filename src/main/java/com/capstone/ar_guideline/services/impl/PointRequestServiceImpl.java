package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.PointRequestResponse.PointRequestCreation;
import com.capstone.ar_guideline.dtos.responses.PointRequestResponse.PointRequestResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.PointRequest;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanyMapper;
import com.capstone.ar_guideline.mappers.PointRequestMapper;
import com.capstone.ar_guideline.repositories.PointRequestRepository;
import com.capstone.ar_guideline.services.ICompanyService;
import com.capstone.ar_guideline.services.IPointRequestService;
import com.capstone.ar_guideline.services.IUserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointRequestServiceImpl implements IPointRequestService {
  private final PointRequestRepository pointRequestRepository;
  private final WalletServiceImpl walletService;
  private final IUserService userService;
  private final ICompanyService companyService;

  @Override
  public List<PointRequestResponse> findAll() {
    try {
      return pointRequestRepository.findAll().stream()
          .map(PointRequestMapper::fromEntityToPointRequestResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
    }
  }

  @Override
  public List<PointRequestResponse> findAllByCompanyId(String companyId) {
    try {
      return pointRequestRepository.findByCompanyIdOrderByCreatedAtDesc(companyId).stream()
          .map(PointRequestMapper::fromEntityToPointRequestResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
    }
  }

  @Override
  public List<PointRequestResponse> findAllByEmployeeId(String employeeId) {
    try {
      return pointRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId).stream()
          .map(PointRequestMapper::fromEntityToPointRequestResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
    }
  }

  @Override
  public PointRequestResponse create(PointRequestCreation pointRequest) {
    try {
      Company company =
          CompanyMapper.fromCompanyResponseToEntity(
              companyService.findById(pointRequest.getCompanyId()));

      User employee = userService.findById(pointRequest.getEmployeeId());
      PointRequest pointRequestEntity =
          PointRequest.builder()
              .reason(pointRequest.getReason())
              .amount(pointRequest.getAmount())
              .company(company)
              .employee(employee)
              .status(ConstStatus.PROCESSING)
              .requestNumber(pointRequest.getRequestNumber())
              .createdAt(LocalDateTime.now())
              .build();

      pointRequestRepository.save(pointRequestEntity);
      return PointRequestMapper.fromEntityToPointRequestResponse(pointRequestEntity);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
    }
  }

  @Override
  public PointRequestResponse update(String requestId, PointRequestCreation request) {
    try {
      if (!pointRequestRepository.existsById(requestId)) {
        throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
      }
      PointRequest pointRequestEntity = pointRequestRepository.findById(requestId).get();

      if (request.getStatus() != null
          && (request.getStatus().equalsIgnoreCase(ConstStatus.APPROVED)
              || request.getStatus().equalsIgnoreCase(ConstStatus.REJECT))) {
        pointRequestEntity.setStatus(request.getStatus());
        pointRequestEntity.setCompletedAt(LocalDateTime.now());
      }

      if (request.getStatus() != null
          && request.getStatus().equalsIgnoreCase(ConstStatus.APPROVED)) {
        walletService.updateBalanceBySend(
            pointRequestEntity.getAmount(),
            pointRequestEntity.getEmployee().getId(),
            userService
                .findCompanyAdminByCompanyId(pointRequestEntity.getCompany().getId())
                .getId());
      }

      pointRequestEntity = pointRequestRepository.save(pointRequestEntity);

      return PointRequestMapper.fromEntityToPointRequestResponse(pointRequestEntity);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.POINT_REQUEST_ERROR);
    }
  }
}
