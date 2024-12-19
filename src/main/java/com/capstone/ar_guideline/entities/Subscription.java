package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
  private List<CompanySubscription> companySubscriptions;

  private String subscriptionCode;
  private Integer duration;
  private String scanTime;
  private String subscriptionStatus;
  private String status;
}
