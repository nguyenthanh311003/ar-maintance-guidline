package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.io.Serializable;
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
public class AssignGuideline implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  // The "guideline" here is a reference to the Course entity
  @ManyToOne
  @JoinColumn(name = "guideline_id", nullable = false)
  private Course guideline;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private User employee;

  @ManyToOne
  @JoinColumn(name = "manager_id", nullable = false)
  private User manager;

  // Status of the assignment: "Done", "InProcess", "Archived"
  @Column(nullable = false)
  private String status;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp private LocalDateTime updatedDate;
}
