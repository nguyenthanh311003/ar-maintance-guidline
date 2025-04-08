package com.capstone.ar_guideline.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PointOptions {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  @Column private Long amount;

  private Long point;

  @OneToMany(mappedBy = "pointOptions")
  @JsonIgnore
  private List<OrderTransaction> orderTransactions;

  @Column(name = "currency", length = 10, columnDefinition = "varchar(10) default 'VND'")
  private String currency = "VND";

  private String status = "ACTIVE";

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
