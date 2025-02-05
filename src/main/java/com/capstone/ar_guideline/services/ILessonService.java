package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.entities.Lesson;
import java.util.List;

public interface ILessonService {
  LessonResponse create(LessonCreationRequest request);

  LessonResponse update(String id, LessonCreationRequest request);

  void delete(String id);

  List<LessonResponse> findByCourseId(String courseId);

  List<Lesson> findByCourseIdReturnEntity(String courseId);

  Lesson findById(String id);

  Integer countByCourseId(String courseId);

  void updateDuration(String lessonId, Integer duration);

  void swapOrder(String id1, String id2);
}
