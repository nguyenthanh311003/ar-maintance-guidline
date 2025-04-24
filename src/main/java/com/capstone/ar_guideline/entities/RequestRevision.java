package com.capstone.ar_guideline.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_revisions")
public class RequestRevision {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id; // Primary key

  @ManyToOne
  @JoinColumn(name = "company_request_id", nullable = false)
  private CompanyRequest companyRequest; // Foreign key to Requests

  @OneToMany(mappedBy = "requestRevision", cascade = CascadeType.ALL)
  private List<RevisionFile> revisionFiles;

  // List of revisions associated with this request revision
  private String reason; // Reason for the revision request

  private String rejectionReason; // Reason for rejection (if applicable)

  private String status; // Status of the revision request (e.g., "PENDING", "COMPLETED")

  private String modelFile;

  @ManyToOne
  @JoinColumn(name = "user_reject_id")
  private User userReject; // User who rejected the request

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedDate;

  private Integer priceProposal; // Price proposed by the designer
}
