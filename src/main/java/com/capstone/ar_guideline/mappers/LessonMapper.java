package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Lesson;

public class LessonMapper {

  public static Lesson fromLessonCreationRequestToEntity(LessonCreationRequest request) {
    return Lesson.builder()
        .course(Course.builder().id(request.getCourseId()).build())
        .title(request.getTitle())
        .description(request.getDescription())
        .duration(request.getDuration())
        .orderInCourse(request.getOrderInCourse())
        .status(request.getStatus())
        .course(Course.builder().id(request.getCourseId()).build())
        .build();
  }

  public static LessonResponse FromEntityToLessonResponse(Lesson lesson) {
    return LessonResponse.builder()
        .id(lesson.getId())
        .courseId(lesson.getCourse().getId())
        .title(lesson.getTitle())
        .description(lesson.getDescription())
        .duration(lesson.getDuration())
        .orderInCourse(lesson.getOrderInCourse())
        .status(lesson.getStatus())
        .build();
  }
}
