package com.capstone.ar_guideline.dtos.responses.LessonDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDetailResponse {

  private String id;
  private String lessonId;
  private String title;
  private Integer orderInLesson;
  private String description;
  private Integer duration;
  private String status;
  private String videoUrl;
  private String content;
  private String attachFileUrl;
  private String type;
}
