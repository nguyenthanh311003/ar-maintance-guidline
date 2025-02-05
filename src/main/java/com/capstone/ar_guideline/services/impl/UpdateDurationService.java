package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.ILessonService;
import com.capstone.ar_guideline.services.IUpdateDurationService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateDurationService implements IUpdateDurationService {
  ICourseService courseService;
  ILessonService lessonService;

  @Override
  public void updateCourseDuration(String courseId) {
    try {
      Course courseById = courseService.findById(courseId);
      List<Lesson> lessonsByCourseId = lessonService.findByCourseIdReturnEntity(courseById.getId());

      int totalDurationCourse = lessonsByCourseId.stream().mapToInt(Lesson::getDuration).sum();

      courseById.setDuration(totalDurationCourse);

      courseService.save(courseById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
    }
  }

  @Override
  public int getDurationOfCourseByCourseId(String courseId) {
    Course courseById = courseService.findById(courseId);
    List<Lesson> lessonsByCourseId = lessonService.findByCourseIdReturnEntity(courseById.getId());

    return lessonsByCourseId.stream().mapToInt(Lesson::getDuration).sum();
  }
}
