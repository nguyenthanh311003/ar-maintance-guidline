package com.capstone.ar_guideline.dtos.responses.Option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionResponse {
  private String id;
  private String questionId;
  private String option;
  private Boolean isRight;
}
