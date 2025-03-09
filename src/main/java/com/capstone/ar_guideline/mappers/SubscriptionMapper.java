package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionMapper {
  public static Subscription fromSubscriptionCreationRequestToEntity(
      SubscriptionCreationRequest request) {
    return Subscription.builder()
        .subscriptionCode(request.getSubscriptionCode())
        .maxNumberOfUsers(request.getMaxNumberOfUsers())
        .maxStorageUsage(request.getMaxStorageUsage())
        .monthlyFee(request.getMonthlyFee())
        .storageUnit("GB")
        .currency("VND")
        .status(request.getStatus())
        .build();
  }

  public static SubscriptionResponse fromEntityToSubscriptionResponse(Subscription subscription) {
    return SubscriptionResponse.builder()
        .id(subscription.getId())
        .subscriptionCode(subscription.getSubscriptionCode())
        .subscriptionCode(subscription.getSubscriptionCode())
        .maxNumberOfUsers(subscription.getMaxNumberOfUsers())
        .maxStorageUsage(subscription.getMaxStorageUsage())
        .storageUnit(subscription.getStorageUnit())
        .currency(subscription.getCurrency())
        .monthlyFee(subscription.getMonthlyFee())
        .status(subscription.getStatus())
        .build();
  }

  public static List<SubscriptionResponse> fromEntitiesToSubscriptionResponses(
      List<Subscription> subscriptions) {
    return subscriptions.stream()
        .map(SubscriptionMapper::fromEntityToSubscriptionResponse)
        .collect(Collectors.toList());
  }
}
