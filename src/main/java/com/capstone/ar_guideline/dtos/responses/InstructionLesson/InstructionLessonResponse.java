package com.capstone.ar_guideline.dtos.responses.InstructionLesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionLessonResponse {
  private String id;

  private String lessonId;

  private String instructionCode;
}
