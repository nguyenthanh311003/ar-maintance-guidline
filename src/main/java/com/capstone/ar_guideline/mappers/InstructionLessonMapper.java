package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.InstructionLesson.InstructionLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionLesson.InstructionLessonResponse;
import com.capstone.ar_guideline.entities.InstructionLesson;
import com.capstone.ar_guideline.entities.Lesson;

public class InstructionLessonMapper {
  public static InstructionLessonResponse toInstructionLessonResponse(
      InstructionLesson modelLesson) {
    return InstructionLessonResponse.builder()
        .id(modelLesson.getId())
        .lessonId(modelLesson.getLesson().getId())
        .instructionCode(modelLesson.getInstructionCode())
        .build();
  }

  public static InstructionLesson toInstructionLesson(
      InstructionLessonCreationRequest modelLessonCreationRequest) {
    return InstructionLesson.builder()
        .lesson(Lesson.builder().id(modelLessonCreationRequest.getLessonId()).build())
        .instructionCode(modelLessonCreationRequest.getInstructionCode())
        .build();
  }
}
