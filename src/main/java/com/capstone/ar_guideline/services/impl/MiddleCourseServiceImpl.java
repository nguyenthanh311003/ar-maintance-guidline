package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MiddleCourseServiceImpl {

  @Autowired @Lazy ICourseService courseService;

  public Course findCourseById(String id) {
    return courseService.findById(id);
  }
}
