package com.capstone.ar_guideline.services;

public interface IUpdateDurationService {
  void updateCourseDuration(String courseId);

  int getDurationOfCourseByCourseId(String courseId);
}
