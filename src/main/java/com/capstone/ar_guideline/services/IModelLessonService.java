package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.requests.ModelLesson.ModelLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.dtos.responses.ModelLesson.ModelLessonResponse;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.entities.ModelLesson;

public interface IModelLessonService {
    ModelLessonResponse create(ModelLessonCreationRequest request);

    ModelLessonResponse update(String id, ModelLessonCreationRequest request);

    void delete(String id);

    ModelLesson findById(String id);
}
