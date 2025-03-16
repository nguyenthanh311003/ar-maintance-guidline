package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WalletTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String type;

  @ManyToOne
  @JoinColumn(name = "wallet_id", referencedColumnName = "id")
  private Wallet wallet;

  @ManyToOne
  @JoinColumn(name = "service_price_id", referencedColumnName = "id")
  private ServicePrice servicePrice;

  private Long amount;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
