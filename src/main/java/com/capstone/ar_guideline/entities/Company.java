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
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<User> users;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
  private List<CompanySubscription> companySubscriptions;

  private String companyName;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
