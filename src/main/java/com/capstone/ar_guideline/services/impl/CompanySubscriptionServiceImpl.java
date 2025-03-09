package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstCommon;
import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.CompanySubscription;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanySubscriptionMapper;
import com.capstone.ar_guideline.payos.CreatePaymentLinkRequestBody;
import com.capstone.ar_guideline.repositories.CompanySubscriptionRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import com.capstone.ar_guideline.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
      CompanySubscription companySubscription =
          findByCompanyIdAndSubscriptionId(request.getCompanyId(), request.getSubscriptionId());

      if (companySubscription != null) {
        return CompanySubscriptionMapper.fromEntityToCompanySubscriptionResponse(
            companySubscription);
      }
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


      newCompanySubscription.setStorageUsage(0.0);
        newCompanySubscription.setNumberOfUsers(0);

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

  private CompanySubscription findByCompanyIdAndSubscriptionId(
      String companyId, String subscriptionId) {
    try {
      CompanySubscription companySubscription =
          companySubscriptionRepository.findByCompanyId(companyId);

      if (companySubscription != null) {
        companySubscription.setSubscriptionStartDate(LocalDateTime.now());
        if(companySubscription.getSubscriptionExpireDate().isBefore(LocalDateTime.now()) && companySubscription.getSubscription().getId().equals(subscriptionId) )
        {
          long daysBetween = java.time.Duration.between(companySubscription.getSubscriptionExpireDate(), LocalDateTime.now()).toDays();
          companySubscription.setSubscriptionExpireDate(LocalDateTime.now().plusDays(30+daysBetween));
        }
        if (companySubscription.getSubscriptionExpireDate().isAfter(LocalDateTime.now()) ||
                companySubscription.getSubscriptionExpireDate().isEqual(LocalDateTime.now())) {
          companySubscription.setSubscriptionExpireDate(LocalDateTime.now().plusDays(30));
        }

        if(!companySubscription.getSubscription().getId().equals(subscriptionId))
        {
          companySubscription.setSubscription(subscriptionService.findById(subscriptionId));
        }

        return companySubscriptionRepository.save(companySubscription);
      } else {
        return null;
      }

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED);
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

  @Override
  public CompanySubscriptionResponse disable(String id) {
    CompanySubscription companySubscriptionById = findById(id);
    companySubscriptionById.setStatus(ConstStatus.INACTIVE_STATUS);
    companySubscriptionById.setSubscriptionStartDate(null);
    companySubscriptionById.setSubscriptionExpireDate(null);
    return CompanySubscriptionMapper.fromEntityToCompanySubscriptionResponse(
        companySubscriptionRepository.save(companySubscriptionById));
  }

  @Override
  public CompanySubscription findByCompanyId(String companyId) {
    try {
      return companySubscriptionRepository.findByCompanyId(companyId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED);
    }
  }

  @Override
  public CompanySubscription findCurrentSubscriptionByCompanyId(String companyId) {
    try {
      if (companySubscriptionRepository.findCurrentSubscriptionByCompanyId(companyId).isEmpty()) {
        throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED);
      }
      return companySubscriptionRepository.findCurrentSubscriptionByCompanyId(companyId).getFirst();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_NOT_EXISTED);
    }
  }

  @Override
  public void updateStorageUsage(String companyId, Double storageUsage, String action) {
    try {
      CompanySubscription companySubscription = findByCompanyId(companyId);
      if (action.equals(ConstCommon.INCREASE)) {
        companySubscription.setStorageUsage(companySubscription.getStorageUsage() + (storageUsage/1000));
      } else {
        companySubscription.setStorageUsage(companySubscription.getStorageUsage() - storageUsage/1000);
      }
      companySubscriptionRepository.save(companySubscription);

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_UPDATE_FAILED);
    }
  }

  @Override
  public void updateNumberOfUsers(String companyId, String action) {
    try {
      CompanySubscription companySubscription = findByCompanyId(companyId);
      if (action.equals(ConstCommon.INCREASE)) {
        companySubscription.setNumberOfUsers(companySubscription.getNumberOfUsers() + 1);
      } else {
        companySubscription.setNumberOfUsers(companySubscription.getNumberOfUsers() - 1);
      }
      companySubscriptionRepository.save(companySubscription);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_UPDATE_FAILED);
    }
  }

  @Override
  public CompanySubscriptionResponse findResponseById(String id) {
    CompanySubscription companySubscription = companySubscriptionRepository.findByCompanyId(id);
    return CompanySubscriptionMapper.fromEntityToCompanySubscriptionResponse(companySubscription);
  }

}
