package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.IEnrollmentService;
import com.capstone.ar_guideline.services.ILessonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MiddleEnrollmentServiceImpl {
  @Autowired @Lazy IEnrollmentService enrollmentService;
  @Autowired @Lazy ICourseService courseService;
  @Autowired @Lazy ILessonService lessonService;

  public Integer countByCourseId(String courseId) {
    return enrollmentService.countByCourseIdAndEnrollmentDateNotNull(courseId);
  }

  public int getDurationOfCourseByCourseId(String courseId) {
    Course courseById = courseService.findById(courseId);
    List<Lesson> lessonsByCourseId = lessonService.findByCourseIdReturnEntity(courseById.getId());

    return lessonsByCourseId.stream().mapToInt(Lesson::getDuration).sum();
  }
}
