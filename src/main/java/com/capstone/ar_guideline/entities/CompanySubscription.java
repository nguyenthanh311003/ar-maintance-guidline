package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "subscription_id", nullable = false)
  private Subscription subscription;

  private LocalDateTime subscriptionStartDate;
  private LocalDateTime subscriptionExpireDate;
  private String status;

  private Integer numberOfUsers;
  private Double storageUsage;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime startDate;

  private LocalDateTime endDate;
}
