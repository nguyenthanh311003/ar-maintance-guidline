package com.capstone.ar_guideline.dtos.responses.Enrollment;

import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
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
  CourseResponse courseResponse;
  String userId;
  LocalDateTime enrollmentDate;
  LocalDateTime deadline;
  Boolean isCompleted;
  LocalDateTime completionDate;
}
