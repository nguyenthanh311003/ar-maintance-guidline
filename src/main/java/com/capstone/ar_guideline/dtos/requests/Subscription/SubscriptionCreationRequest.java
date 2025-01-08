package com.capstone.ar_guideline.dtos.requests.Subscription;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionCreationRequest {
  String subscriptionCode;
  Integer duration;
  Integer scanTime;
  String status;
}
