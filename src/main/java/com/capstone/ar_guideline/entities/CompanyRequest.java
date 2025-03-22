package com.capstone.ar_guideline.entities;

import com.capstone.ar_guideline.configurations.CompanyRequestListener;
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
@EntityListeners(CompanyRequestListener.class)
@Table(name = "company_request")
public class CompanyRequest implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "request_id", updatable = false, nullable = false)
  private String requestId;

  private String requestSubject;

  private String requestDescription;

  @ManyToOne(optional = false)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "designer_id")
  private User designer;

  @ManyToOne
  @JoinColumn(name = "requester_id")
  private User requester;

  @ManyToOne
  @JoinColumn(name = "machine_type_id")
  private ModelType machineType;

  @ManyToOne
  @JoinColumn(name = "asset_model_id")
  private Model assetModel;

  @Column(name = "status", length = 20)
  private String status;

  @Column(name = "request_number", nullable = false, updatable = false, unique = true)
  private Long requestNumber;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;
}
