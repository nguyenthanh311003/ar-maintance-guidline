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

  String companyId;
  String title;
  String description;
  String shortDescription;
  String targetAudience;
  Integer duration;
  String imageUrl;
  String status;
  Boolean isMandatory;
  String type;
}
