package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;

public interface ISubscriptionService {
  SubscriptionResponse create(SubscriptionCreationRequest request);

  SubscriptionResponse update(String id, SubscriptionCreationRequest request);

  void delete(String id);

  Subscription findById(String id);
}
