package com.capstone.ar_guideline.dtos.responses.Quiz;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResponse {
  String id;
  String courseId;
  String title;
  String description;
}
