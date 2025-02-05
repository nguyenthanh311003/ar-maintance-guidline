package com.capstone.ar_guideline.dtos.requests.InstructionLesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionLessonCreationRequest {

  private String lessonId;

  private String instructionCode;
}
