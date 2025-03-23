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

  @ManyToOne
  @JoinColumn(name = "point_options_id", referencedColumnName = "id")
  private PointOptions pointOptions;

  @ManyToOne
  @JoinColumn(name = "guideline_id", referencedColumnName = "id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "reciver_id", referencedColumnName = "id")
  private User receiver;

  @ManyToOne
  @JoinColumn(name = "sender_id", referencedColumnName = "id")
  private User sender;

  private Long amount;

  private Long balance;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
