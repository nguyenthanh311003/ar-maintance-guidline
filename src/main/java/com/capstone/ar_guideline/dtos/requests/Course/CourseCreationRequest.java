package com.capstone.ar_guideline.dtos.requests.Course;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {

  String companyId;
  String modelId;
  String title;
  String description;
  String shortDescription;
  String targetAudience;
  Integer duration;
  MultipartFile imageUrl;
  String imageUrlString;
  String status;
  Boolean isMandatory;
  String type;
  String machineTypeId;
}
