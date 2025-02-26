package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;

public class SubscriptionMapper {
  public static Subscription fromSubscriptionCreationRequestToEntity(
      SubscriptionCreationRequest request) {
    return Subscription.builder()
        .subscriptionCode(request.getSubscriptionCode())
        .maxEmployees(request.getMaxEmployees())
        .maxModels(request.getMaxModels())
        .monthlyFee(request.getMonthlyFee())
        .extraModelFee(request.getExtraModelFee())
        .status(request.getStatus())
        .build();
  }

  public static SubscriptionResponse fromEntityToSubscriptionResponse(Subscription subscription) {
    return SubscriptionResponse.builder()
        .id(subscription.getId())
        .subscriptionCode(subscription.getSubscriptionCode())
        .subscriptionCode(subscription.getSubscriptionCode())
        .maxEmployees(subscription.getMaxEmployees())
        .maxModels(subscription.getMaxModels())
        .monthlyFee(subscription.getMonthlyFee())
        .extraModelFee(subscription.getExtraModelFee())
        .status(subscription.getStatus())
        .build();
  }
}
