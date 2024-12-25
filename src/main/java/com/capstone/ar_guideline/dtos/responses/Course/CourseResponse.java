package com.capstone.ar_guideline.dtos.responses.Course;

import jakarta.persistence.*;
import java.io.Serializable;
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
  //    private List<Lesson> lessons;
  private String title;
  private String description;
  private Integer duration;
  private Boolean isMandatory;
  private String status;
  private String type;
}
