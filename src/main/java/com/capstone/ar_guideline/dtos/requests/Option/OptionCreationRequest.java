package com.capstone.ar_guideline.dtos.requests.Option;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionCreationRequest {
  private String id;
  private String questionId;
  private String option;
  private Boolean isRight;
}
