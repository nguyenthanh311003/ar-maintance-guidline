package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.InstructionLesson.InstructionLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionLesson.InstructionLessonResponse;
import com.capstone.ar_guideline.entities.InstructionLesson;

public interface IInstructionLessonService {
  InstructionLessonResponse create(InstructionLessonCreationRequest request);

  InstructionLessonResponse update(String id, InstructionLessonCreationRequest request);

  void delete(String id);

  InstructionLesson findById(String id);
}
