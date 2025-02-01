package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.LessonDetail.LessonDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.entities.LessonDetail;

public class LessonDetailMapper {

    public static LessonDetailResponse fromEntityToLessonDetailResponse(
        LessonDetail lessonDetail) {
        return LessonDetailResponse.builder()
            .id(lessonDetail.getId())
            .lessonId(lessonDetail.getLesson().getId())
                .title(lessonDetail.getTitle())
                .description(lessonDetail.getDescription())
                .duration(lessonDetail.getDuration())
                .status(lessonDetail.getStatus())
                .videoUrl(lessonDetail.getVideoUrl())
                .type(lessonDetail.getType())
            .orderInLesson(lessonDetail.getOrderInLesson())
            .build();
    }

    public static LessonDetail fromLessonDetailCreationRequestToEntity(
        LessonDetailCreationRequest request) {
        return LessonDetail.builder()
            .lesson(Lesson.builder().id(request.getLessonId()).build())
            .title(request.getTitle())
            .description(request.getDescription())
            .duration(request.getDuration())
            .videoUrl(request.getVideoUrl())
            .type(request.getType())
            .orderInLesson(request.getOrderInLesson())
            .status(request.getStatus())
            .build();
    }

}
