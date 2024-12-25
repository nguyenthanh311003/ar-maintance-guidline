package com.capstone.ar_guideline.dtos.requests.Course;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {

  private String id;
  private String companyId;
  private String title;
  private String description;
  private Integer duration;
  private String status;
  private Boolean isMandatory;
  private String type;
}
