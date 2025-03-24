package com.capstone.ar_guideline.entities;

import com.capstone.ar_guideline.configurations.PointRequestListener;
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
@Table(name = "point_request")
@EntityListeners(PointRequestListener.class)
public class PointRequest implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  private String reason;

  private Long amount;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private User employee;

  private String status;

  @Column(name = "request_number", nullable = false, updatable = false, unique = true)
  private String requestNumber;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;
}
