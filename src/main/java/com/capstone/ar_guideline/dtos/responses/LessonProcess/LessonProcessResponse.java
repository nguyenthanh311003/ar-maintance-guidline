package com.capstone.ar_guideline.dtos.responses.LessonProcess;

import java.io.Serializable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonProcessResponse implements Serializable {
  String id;
  String lessonDetailId;
  String userId;
  Boolean isCompleted;
  String completeDate;
}
