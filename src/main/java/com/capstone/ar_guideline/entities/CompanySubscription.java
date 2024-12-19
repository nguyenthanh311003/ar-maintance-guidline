package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompanySubscription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "companySubscription_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "subscription_id", nullable = false)
  private Subscription subscription;

  private String subscriptionStartDate;
  private String subscriptionExpireDate;
  private String status;
}
