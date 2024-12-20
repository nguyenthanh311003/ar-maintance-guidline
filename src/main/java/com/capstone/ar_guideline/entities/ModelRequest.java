package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ModelRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @ManyToOne
  @JoinColumn(name = "modelRequestService_id", nullable = false)
  private ModelRequestService modelRequestService;

  @OneToMany(mappedBy = "modelRequest", cascade = CascadeType.ALL)
  private List<RequestRecipient> requestRecipients;

  private String status;
  private String description;
  private String notes;
  private Double price;
  private Boolean companyApproval;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;
}
