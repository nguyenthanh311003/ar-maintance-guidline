package com.capstone.ar_guideline.dtos.requests.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseListRequest {
  private int page;
  private int size;
  private String searchTemp;
  private String status;
}
