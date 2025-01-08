package com.capstone.ar_guideline.dtos.responses.Enrollment;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollmentResponse {
  String id;
  String courseId;
  String userId;
  Boolean isCompleted;
  LocalDateTime completionDate;
}
