package com.capstone.ar_guideline.dtos.requests.LessonProcess;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonProcessCreationRequest {
  private String lessonDetailId;
  private String userId;
  private Boolean isCompleted;
  private String completeDate;
}
