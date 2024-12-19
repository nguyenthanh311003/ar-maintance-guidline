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
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
  private List<OrderTransaction> orderTransactions;

  private String methodName;
  private String methodCode;
}
