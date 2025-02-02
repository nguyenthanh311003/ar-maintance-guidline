package com.capstone.ar_guideline.dtos.responses.Course;

import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import jakarta.persistence.*;
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
  private String id;
  private String companyId;
  private List<LessonResponse> lessons;
  private String title;
  private String description;
  private Integer duration;
  private Boolean isMandatory;
  private String imageUrl;
  private Integer numberOfLessons;
  private Integer numberOfParticipants;
  private String status;
  private String type;
}
