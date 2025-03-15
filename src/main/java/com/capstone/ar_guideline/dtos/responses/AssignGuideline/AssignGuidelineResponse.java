package com.capstone.ar_guideline.dtos.responses.AssignGuideline;

import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignGuidelineResponse {
  private String id;

  private CourseResponse guideline;

  private UserResponse employee;

  private UserResponse manager;

  private String status;

  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;
}
