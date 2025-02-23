package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.CompanySubscription;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanySubscriptionMapper;
import com.capstone.ar_guideline.repositories.CompanySubscriptionRepository;
import com.capstone.ar_guideline.services.ICompanyService;
import com.capstone.ar_guideline.services.ICompanySubscriptionService;
import com.capstone.ar_guideline.services.ISubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CompanySubscriptionServiceImpl implements ICompanySubscriptionService {
  CompanySubscriptionRepository companySubscriptionRepository;
  ICompanyService companyService;
  ISubscriptionService subscriptionService;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_COMPANY_SUBSCRIPTION};

  @Override
  public CompanySubscriptionResponse create(ComSubscriptionCreationRequest request) {
    try {
      Company companyById = companyService.findByIdReturnEntity(request.getCompanyId());

      Subscription subscriptionById =
          subscriptionService.findByIdAndStatus(
              request.getSubscriptionId(), ConstStatus.ACTIVE_STATUS);

      CompanySubscription newCompanySubscription =
          CompanySubscriptionMapper.fromComSubscriptionCreationRequestToEntity(
              request, companyById, subscriptionById);
      newCompanySubscription.setStatus(ConstStatus.ACTIVE_STATUS);

      newCompanySubscription.setSubscriptionStartDate(LocalDateTime.now());
        newCompanySubscription.setSubscriptionExpireDate(
            newCompanySubscription.getSubscriptionStartDate().plusMonths(1));

      newCompanySubscription = companySubscriptionRepository.save(newCompanySubscription);

      return CompanySubscriptionMapper.fromEntityToCompanySubscriptionResponse(
          newCompanySubscription);

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_CREATE_FAILED);
    }
  }

  @Override
  public CompanySubscriptionResponse update(String id, ComSubscriptionCreationRequest request) {
    try {
      CompanySubscription companySubscriptionById = findById(id);

      Company companyById = companyService.findByIdReturnEntity(request.getCompanyId());

      Subscription subscriptionById =
          subscriptionService.findByIdAndStatus(
              request.getSubscriptionId(), ConstStatus.ACTIVE_STATUS);

      companySubscriptionById.setCompany(companyById);
      companySubscriptionById.setSubscription(subscriptionById);

      companySubscriptionById = companySubscriptionRepository.save(companySubscriptionById);

      return CompanySubscriptionMapper.fromEntityToCompanySubscriptionResponse(
          companySubscriptionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      CompanySubscription companySubscriptionById = findById(id);

      companySubscriptionRepository.deleteById(companySubscriptionById.getId());

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_DELETE_FAILED);
    }
  }

  @Override
  public CompanySubscription findById(String id) {
    try {
      return companySubscriptionRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED);
    }
  }
}
