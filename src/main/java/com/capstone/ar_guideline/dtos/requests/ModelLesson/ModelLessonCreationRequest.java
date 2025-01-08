package com.capstone.ar_guideline.dtos.requests.ModelLesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelLessonCreationRequest {
  private String id;

  private String lessonId;

  private String instructionCode;
}
