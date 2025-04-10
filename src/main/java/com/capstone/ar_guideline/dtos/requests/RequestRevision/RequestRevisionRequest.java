package com.capstone.ar_guideline.dtos.requests.RequestRevision;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestRevisionRequest {

  private String id; // ID of the revision
  private String companyRequestId; // ID of the company request
  private String reason; // Reason for the revision request
  private String status; // Status of the revision request (e.g., "PENDING", "COMPLETED")
  private Integer priceProposal; // Price proposed by the designer
  private String rejectionReason; // Reason for rejection (if applicable)
  private String userRejectId; // ID of the user who rejected the request
  private MultipartFile modelFile;
  private List<MultipartFile> revisionFiles; // List of files associated with the revision request
}
