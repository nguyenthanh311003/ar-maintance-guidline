package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.ModelLesson.ModelLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelLesson.ModelLessonResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.entities.ModelLesson;

public class ModelLessonMapper {
    public static ModelLessonResponse toModelLessonResponse(ModelLesson modelLesson) {
        return ModelLessonResponse.builder()
                .id(modelLesson.getId())
                .lessonId(modelLesson.getLesson().getId())
                .instructionCode(modelLesson.getInstructionCode())
                .build();
    }

    public static ModelLesson toModelLesson(ModelLessonCreationRequest modelLessonCreationRequest) {
        return ModelLesson.builder()
                .id(modelLessonCreationRequest.getId())
                .lesson(Lesson.builder().id(modelLessonCreationRequest.getLessonId()).build())
                .instructionCode(modelLessonCreationRequest.getInstructionCode())
                .build();
    }
}
