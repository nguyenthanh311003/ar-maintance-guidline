package com.capstone.ar_guideline.dtos.requests.Quiz;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizCreationRequest {
  String courseId;
  String title;
  String description;
}
