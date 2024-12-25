package com.capstone.ar_guideline.dtos.responses.Company;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponse implements Serializable {
  private String id;
  private String companyName;
}
