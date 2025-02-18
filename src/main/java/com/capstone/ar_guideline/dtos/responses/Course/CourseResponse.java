package com.capstone.ar_guideline.dtos.responses.Course;

import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse implements Serializable {
  String id;
  String companyId;
  String modelId;
  List<LessonResponse> lessons;
  String modelCode;
  String title;
  String description;
  String shortDescription;
  String targetAudience;
  Integer duration;
  Boolean isMandatory;
  String imageUrl;
  Integer numberOfLessons;
  Integer numberOfParticipants;
  String status;
  String type;
}
