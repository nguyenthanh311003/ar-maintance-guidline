package com.capstone.ar_guideline.dtos.requests.Lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonCreationRequest {
  private String courseId;
  private String title;
  private String description;
  private Integer duration;
  private String status;
}
