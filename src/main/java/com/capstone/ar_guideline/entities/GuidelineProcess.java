package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GuidelineProcess implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  // Reference to the AssignGuideline entity
  @ManyToOne
  @JoinColumn(name = "assign_id", nullable = false)
  private AssignGuideline assignGuideline;

  // Status for the process: "PENDING", "FAIL", "PASS"
  @Column(nullable = false)
  private String status;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;
}
