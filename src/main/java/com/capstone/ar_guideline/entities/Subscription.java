package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Column(unique = true)
  private String subscriptionCode;

  private Integer maxNumberOfUsers;
  private Double maxStorageUsage;
  private Double monthlyFee;
  private String storageUnit = "GB";
  private String currency = "VND";
  private String status;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
