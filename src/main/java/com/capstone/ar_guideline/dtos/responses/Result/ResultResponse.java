package com.capstone.ar_guideline.dtos.responses.Result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultResponse {
  String id;
  String quizId;
  String userId;
}
