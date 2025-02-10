package com.capstone.ar_guideline.dtos.requests.LessonDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

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
  private MultipartFile videoUrl;
  private MultipartFile attachFileUrl;
  private String videoUrlString;
  private String attachFileUrlString;
  private String type;
}
