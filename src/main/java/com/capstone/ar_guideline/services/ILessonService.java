package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.entities.Lesson;

public interface ILessonService {
  LessonResponse create(LessonCreationRequest request);

  LessonResponse update(String id, LessonCreationRequest request);

  void delete(String id);

  Lesson findById(String id);

  Integer countByCourseId(String courseId);
}
