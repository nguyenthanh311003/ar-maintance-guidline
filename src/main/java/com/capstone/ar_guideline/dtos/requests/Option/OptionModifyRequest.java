package com.capstone.ar_guideline.dtos.requests.Option;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionModifyRequest {
  String optionId;
  String option;
  Boolean isRight;
}
