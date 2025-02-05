package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.LessonProcess.LessonProcessResponse;
import com.capstone.ar_guideline.entities.*;

public class LessonProcessMapper {
  public static LessonProcessResponse fromEntityToLessonProcessResponse(
      LessonProcess lessonProcess) {
    return LessonProcessResponse.builder()
        .id(lessonProcess.getId())
        .lessonDetailId(lessonProcess.getLessonDetail().getId())
        .userId(lessonProcess.getUser().getId())
        .isCompleted(lessonProcess.getIsCompleted())
        .build();
  }

  public static LessonProcess fromLessonProcessCreationRequestToEntity(
      LessonProcessCreationRequest request) {
    return LessonProcess.builder()
        .user(User.builder().id(request.getUserId()).build())
        .lessonDetail(LessonDetail.builder().id(request.getLessonDetailId()).build())
        .isCompleted(request.getIsCompleted())
        .build();
  }
}
