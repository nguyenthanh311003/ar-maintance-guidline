package com.capstone.ar_guideline.dtos.responses.Subscription;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionResponse {
  String id;
  String subscriptionCode;
  Integer maxNumberOfUsers;
  Double maxStorageUsage;
  Double monthlyFee;
  String status;
  String storageUnit;
  String currency;
}
