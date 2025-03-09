package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.entities.Subscription;
import java.util.List;

public interface ISubscriptionService {
  SubscriptionResponse create(SubscriptionCreationRequest request);

  List<SubscriptionResponse> findAll();

  SubscriptionResponse update(String id, SubscriptionCreationRequest request);

  void delete(String id);

  Subscription findById(String id);

  Subscription findByIdAndStatus(String id, String status);

  Subscription findByCode(String code);
}
