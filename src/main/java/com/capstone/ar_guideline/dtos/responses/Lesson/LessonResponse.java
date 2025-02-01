package com.capstone.ar_guideline.dtos.responses.Lesson;

import java.io.Serializable;
import java.util.List;

import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse implements Serializable {
  String id;
  String courseId;
  String title;
  Integer orderInCourse;
  String description;
  Integer duration;
  String status;
  List<LessonDetailResponse> lessonDetails;
}
