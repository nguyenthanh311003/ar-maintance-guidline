package com.capstone.ar_guideline.dtos.responses.Company;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseManagement implements Serializable {
  private String id;
  private String companyName;
  private Long numberOfAccount;
  private Long numberOfGuideline;
  private LocalDateTime createdDate;
}
