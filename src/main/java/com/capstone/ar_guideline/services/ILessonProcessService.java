package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.LessonProcess.LessonProcessResponse;
import com.capstone.ar_guideline.entities.LessonProcess;

import java.util.List;

public interface ILessonProcessService {
  LessonProcessResponse create(LessonProcessCreationRequest request);

  LessonProcessResponse update(String id, LessonProcessCreationRequest request);

  void delete(String id);

  LessonProcess findById(String id);

  void createAll(String courseId,String userId);
}
