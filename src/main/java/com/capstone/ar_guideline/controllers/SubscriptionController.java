package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Subscription.SubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import com.capstone.ar_guideline.services.ISubscriptionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubscriptionController {
  ISubscriptionService subscriptionService;

  @PostMapping(value = ConstAPI.SubscriptionAPI.CREATE_SUBSCRIPTION)
  ApiResponse<SubscriptionResponse> createSubscription(
      @RequestBody @Valid SubscriptionCreationRequest request) {
    return ApiResponse.<SubscriptionResponse>builder()
        .result(subscriptionService.create(request))
        .build();
  }


  @GetMapping(value = ConstAPI.SubscriptionAPI.CREATE_SUBSCRIPTION)
  ApiResponse<List<SubscriptionResponse>> findAll() {
    return ApiResponse.<List<SubscriptionResponse>>builder()
            .result(subscriptionService.findAll())
            .build();
  }


  @PutMapping(value = ConstAPI.SubscriptionAPI.UPDATE_SUBSCRIPTION + "{subscriptionId}")
  ApiResponse<SubscriptionResponse> updateSubscription(
      @PathVariable String subscriptionId, @RequestBody SubscriptionCreationRequest request) {
    return ApiResponse.<SubscriptionResponse>builder()
        .result(subscriptionService.update(subscriptionId, request))
        .build();
  }

  @DeleteMapping(value = ConstAPI.SubscriptionAPI.DELETE_SUBSCRIPTION + "{subscriptionId}")
  ApiResponse<String> deleteSubscription(@PathVariable String subscriptionId) {
    subscriptionService.delete(subscriptionId);
    return ApiResponse.<String>builder().result("Subscription detail has been deleted").build();
  }
}
