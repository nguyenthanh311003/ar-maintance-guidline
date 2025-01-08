package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.SubscriptionMapper;
import com.capstone.ar_guideline.repositories.SubscriptionRepository;
import com.capstone.ar_guideline.services.ISubscriptionService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubscriptionService implements ISubscriptionService {
  SubscriptionRepository subscriptionRepository;
  RedisTemplate<String, Object> redisTemplate;

  private final String[] keysToRemove = {
    ConstHashKey.HASH_KEY_SUBSCRIPTION, ConstHashKey.HASH_KEY_COMPANY_SUBSCRIPTION
  };

  @Override
  public SubscriptionResponse create(SubscriptionCreationRequest request) {
    try {
      Subscription newSubscription =
          SubscriptionMapper.fromSubscriptionCreationRequestToEntity(request);
      newSubscription.setStatus(ConstStatus.ACTIVE_STATUS);

      newSubscription = subscriptionRepository.save(newSubscription);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return SubscriptionMapper.fromEntityToSubscriptionResponse(newSubscription);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.SUBSCRIPTION_CREATE_FAILED);
    }
  }

  @Override
  public SubscriptionResponse update(String id, SubscriptionCreationRequest request) {
    try {
      Subscription subscriptionById = findById(id);

      subscriptionById.setSubscriptionCode(request.getSubscriptionCode());
      subscriptionById.setDuration(request.getDuration());
      subscriptionById.setScanTime(request.getScanTime());
      subscriptionById.setStatus(request.getStatus());

      subscriptionById = subscriptionRepository.save(subscriptionById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return SubscriptionMapper.fromEntityToSubscriptionResponse(subscriptionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.SUBSCRIPTION_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Subscription subscriptionById = findById(id);
      subscriptionRepository.deleteById(subscriptionById.getId());
      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED);
    }
  }

  @Override
  public Subscription findById(String id) {
    try {
      return subscriptionRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED);
    }
  }
}
