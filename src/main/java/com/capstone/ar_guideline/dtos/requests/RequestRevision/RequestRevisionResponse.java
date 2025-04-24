package com.capstone.ar_guideline.dtos.requests.RequestRevision;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestRevisionResponse {

  private String id; // ID of the revision
  private String companyRequestId; // ID of the company request
  private String reason; // Reason for the revision request
  private String rejectionReason; // Reason for rejection (if applicable)
  private String status; // Status of the revision request (e.g., "PENDING", "COMPLETED")
  private Integer priceProposal; // Price proposed by the designer
  private List<String> revisionFiles; // List of files associated with the revision request
  private LocalDateTime createdDate; // Date when the revision request was created
}
