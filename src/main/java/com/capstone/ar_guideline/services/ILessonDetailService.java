package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.LessonDetail.LessonDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import com.capstone.ar_guideline.entities.LessonDetail;
import java.util.List;

public interface ILessonDetailService {

  LessonDetailResponse create(LessonDetailCreationRequest lessonDetailCreationRequest);

  LessonDetailResponse update(String id, LessonDetailCreationRequest lessonDetailCreationRequest);

  LessonDetail findById(String id);

  void delete(String id);

  List<LessonDetailResponse> findAllByLessonId(String lessonId);

  void swapOrder(String id1, String id2);
}
