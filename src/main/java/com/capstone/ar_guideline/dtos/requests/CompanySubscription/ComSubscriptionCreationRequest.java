package com.capstone.ar_guideline.dtos.requests.CompanySubscription;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComSubscriptionCreationRequest {
  String companyId;
  String subscriptionId;
  LocalDateTime subscriptionStartDate;
  LocalDateTime subscriptionExpireDate;
  String status;
}
