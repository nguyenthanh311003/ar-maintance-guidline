package com.capstone.ar_guideline.dtos.responses.Vuforia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasetStatusResponse {
  private String status;
  private String uuid;
  private String createdAt;
}
