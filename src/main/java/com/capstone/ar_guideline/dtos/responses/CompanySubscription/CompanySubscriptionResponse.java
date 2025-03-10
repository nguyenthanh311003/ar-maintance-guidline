package com.capstone.ar_guideline.dtos.responses.CompanySubscription;

import java.time.LocalDateTime;

import com.capstone.ar_guideline.dtos.responses.Subscription.SubscriptionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanySubscriptionResponse {
  String id;
  String companyId;
  SubscriptionResponse subscriptionResponse;
  Double monthlyFee;
  Double storageUsage;
  Integer numberOfUsers;
  LocalDateTime subscriptionStartDate;
  LocalDateTime subscriptionExpireDate;
  String status;
}
