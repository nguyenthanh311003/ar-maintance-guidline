package com.capstone.ar_guideline.dtos.requests.LessonDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDetailCreationRequest {

  private String lessonId;
  private String title;
  private String description;
  private Integer duration;
  private String status;
  private String videoUrl;
  private String type;
}
