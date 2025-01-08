package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;

public class SubscriptionMapper {
  public static Subscription fromSubscriptionCreationRequestToEntity(
      SubscriptionCreationRequest request) {
    return Subscription.builder()
        .subscriptionCode(request.getSubscriptionCode())
        .duration(request.getDuration())
        .scanTime(request.getScanTime())
        .build();
  }

  public static SubscriptionResponse fromEntityToSubscriptionResponse(Subscription subscription) {
    return SubscriptionResponse.builder()
        .id(subscription.getId())
        .subscriptionCode(subscription.getSubscriptionCode())
        .duration(subscription.getDuration())
        .scanTime(subscription.getScanTime())
        .status(subscription.getStatus())
        .build();
  }
}
