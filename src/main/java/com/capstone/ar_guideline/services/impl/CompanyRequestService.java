package com.capstone.ar_guideline.services.impl;

import static com.capstone.ar_guideline.constants.ConstStatus.*;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanyMapper;
import com.capstone.ar_guideline.mappers.CompanyRequestMapper;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import com.capstone.ar_guideline.services.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyRequestService implements ICompanyRequestService {
  private final CompanyRequestRepository companyRequestRepository;
  private final ICompanyService companyService;
  private final IUserService userService;
  private final IMachineService machineService;
  private final IModelService assetModelService;
  private final EmailService emailService;

  @Override
  public List<CompanyRequestResponse> findAll() {
    try {
      return companyRequestRepository.findAll().stream()
          .map(CompanyRequestMapper::fromEntityToResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public List<CompanyRequestResponse> findByCompanyId(String companyId) {
    try {
      return companyRequestRepository.findByCompany_IdOrderByCreatedAtDesc(companyId).stream()
          .map(CompanyRequestMapper::fromEntityToResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public List<CompanyRequestResponse> findByDesignerId(String designerId) {
    try {
      return companyRequestRepository.findByDesigner_IdOrderByCreatedAtDesc(designerId).stream()
          .map(CompanyRequestMapper::fromEntityToResponse)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public CompanyRequestResponse findById(String id) {
    try {
      return CompanyRequestMapper.fromEntityToResponse(
          companyRequestRepository.findByRequestId(id));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public CompanyRequestResponse create(CompanyRequestCreation request) {
    try {
      Company company =
          CompanyMapper.fromCompanyResponseToEntity(
              companyService.findById(request.getCompanyId()));
      User requester = userService.findById(request.getRequestId());
      Machine machine = machineService.findById(request.getMachineId());
      CompanyRequest companyRequest = CompanyRequestMapper.fromCreationRequestToEntity(request);
      companyRequest.setCompany(company);
      companyRequest.setMachine(machine);
      companyRequest.setDesigner(null);
      companyRequest.setStatus(PENDING);
      companyRequest.setRequester(requester);
      companyRequest = companyRequestRepository.save(companyRequest);
      return CompanyRequestMapper.fromEntityToResponse(companyRequest);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public CompanyRequestResponse update(String requestId, CompanyRequestCreation request) {
    try {
      CompanyRequest companyRequest = companyRequestRepository.findByRequestId(requestId);
      if (request.getStatus() != null) companyRequest.setStatus(request.getStatus());
      if (request.getDesignerId() != null)
        companyRequest.setDesigner(userService.findById(request.getDesignerId()));

      if (request.getAssetModelId() != null) {
        companyRequest.setAssetModel(assetModelService.findById(request.getAssetModelId()));
      }

      if (request.getStatus().equalsIgnoreCase(DRAFTED) && companyRequest.getAssetModel() != null) {
        Model assetModel = companyRequest.getAssetModel();
        assetModel.setStatus(DRAFTED);
        assetModelService.update(assetModel);
      }

      if (request.getStatus().equalsIgnoreCase(PROCESSING)
          && Objects.nonNull(request.getRequesterId())
          && Objects.nonNull(companyRequest.getRequester())) {
        emailService.sendCompanyRequestEmail(request.getRequesterId(), companyRequest);
      }

      if (request.getStatus().equalsIgnoreCase(APPROVED)
          && companyRequest.getAssetModel() != null) {
        Model assetModel = companyRequest.getAssetModel();
        assetModel.setStatus(ACTIVE_STATUS);
        assetModelService.update(assetModel);
      }

      if (request.getStatus().equalsIgnoreCase(COMPANY_CANCELLED)
          || request.getStatus().equalsIgnoreCase(DESIGNER_CANCELLED)) {
        companyRequest.setCancelledAt(LocalDateTime.now());
      }

      if (request.getStatus().equalsIgnoreCase(DESIGNER_CANCELLED)) {
        emailService.sendDesignerCancelledEmail(
            companyRequest.getRequester().getEmail(), companyRequest);
      }

      companyRequest = companyRequestRepository.save(companyRequest);
      return CompanyRequestMapper.fromEntityToResponse(companyRequest);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
    }
  }

  @Override
  public void delete(Long id) {}
}
