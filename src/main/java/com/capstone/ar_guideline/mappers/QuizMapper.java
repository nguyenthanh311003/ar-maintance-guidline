package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Quiz.QuizCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Quiz;

public class QuizMapper {

    public static Quiz fromQuizCreationRequestToEntity(QuizCreationRequest request) {
        return Quiz.builder()
                .course(Course.builder().id(request.getCourseId()).build())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    public static QuizResponse fromEntityToQuizResponse(Quiz quiz) {
        return QuizResponse.builder()
                //.id(quiz.getId())
                .courseId(quiz.getCourse().getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .build();
    }
}