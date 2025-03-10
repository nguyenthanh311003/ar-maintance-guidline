package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;

public interface IARGuidelineService {
  InstructionResponse createInstruction(InstructionCreationRequest request);

  PagingModel<InstructionResponse> getInstructionsByCourseId(int page, int size, String courseId);

  CourseResponse findCourseById(String modelId);

  PagingModel<ModelResponse> findModelByCompanyId(
      int page, int size, String companyId, String type, String name, String code);

  ModelResponse findModelById(String id);

  Boolean deleteInstructionById(String instructionId);

  Boolean deleteModelById(String modelId);

  void deleteCourseById(String courseId);

  ModelResponse updateModel(String id, ModelCreationRequest request);

  void changeStatusCourse(String courseId);
}
