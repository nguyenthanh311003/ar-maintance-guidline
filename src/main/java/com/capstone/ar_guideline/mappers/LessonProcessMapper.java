package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.LessonProcess.LessonProcessResponse;
import com.capstone.ar_guideline.entities.*;

import java.util.stream.Collectors;

public class LessonProcessMapper {
    public static LessonProcessResponse fromEntityToLessonProcessResponse(LessonProcess lessonProcess) {
        return LessonProcessResponse.builder()
                .id(lessonProcess.getId())
                .lessonId(lessonProcess.getLesson().getId())
                .userId(lessonProcess.getUser().getId())
                .isCompleted(lessonProcess.getIsCompleted())
                .completeDate(lessonProcess.getCompleteDate())
                .build();
    }

    public static LessonProcess fromLessonProcessCreationRequestToEntity(LessonProcessCreationRequest request) {
        return LessonProcess.builder()
                .lesson(Lesson.builder().id(request.getLessonId()).build())
                .user(User.builder().id(request.getUserId()).build())
                .isCompleted(request.getIsCompleted())
                .completeDate(request.getCompleteDate())
                .build();
    }
}
