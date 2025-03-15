package com.capstone.ar_guideline.dtos.requests.AssignGuideline;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignGuidelineCreationRequest {
  String guidelineId;
  String employeeId;
  String managerId;
}
