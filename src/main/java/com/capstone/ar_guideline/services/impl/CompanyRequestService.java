package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.CompanyRequest;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanyMapper;
import com.capstone.ar_guideline.mappers.CompanyRequestMapper;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import com.capstone.ar_guideline.services.ICompanyRequestService;
import com.capstone.ar_guideline.services.ICompanyService;
import com.capstone.ar_guideline.services.IUserService;
import java.util.List;
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
      return companyRequestRepository.findByCompany_Id(companyId).stream()
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
      return companyRequestRepository.findByDesigner_Id(designerId).stream()
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
      CompanyRequest companyRequest = CompanyRequestMapper.fromCreationRequestToEntity(request);
      companyRequest.setCompany(company);
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
  public CompanyRequestResponse update(Long id, CompanyRequestCreation request) {
    return null;
  }

  @Override
  public void delete(Long id) {}
}
