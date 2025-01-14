package com.capstone.ar_guideline.dtos.responses.CompanySubscription;

import java.time.LocalDateTime;
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
  String subscriptionId;
  LocalDateTime subscriptionStartDate;
  LocalDateTime subscriptionExpireDate;
  String status;
}
