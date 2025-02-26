package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;

public interface IARGuidelineService {
  InstructionResponse createInstruction(InstructionCreationRequest request);

  PagingModel<InstructionResponse> getInstructionsByCourseId(int page, int size, String courseId);

  CourseResponse findCourseById(String modelId);
}
