package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineModifyRequest;
import com.capstone.ar_guideline.dtos.requests.MachineType.MachineTypeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Machine;
import java.util.List;

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

  PagingModel<MachineResponse> getMachinesByCompanyId(int page, int size, String companyId, String keyword, String machineTypeName);

  MachineResponse createMachine(MachineCreationRequest request);

  MachineTypeAttributeResponse createMachineTypeAttribute(
      MachineTypeAttributeCreationRequest request);

  List<MachineTypeValueResponse> getMachineTypeAttributeByMachineTypeId(
      String machineTypeId, String companyId);

  MachineResponse getMachineById(String machineId);

  MachineResponse getMachineByCode(String machineCode);

  MachineResponse updateMachineById(String machineId, MachineModifyRequest request);

  MachineTypeResponse createMachineType(MachineTypeCreationRequest request);

  PagingModel<MachineTypeResponse> getMachineTypesByCompanyId(int page, int size, String companyId, String name);

  MachineTypeResponse getMachineTypesById(String machineTypeId);

  MachineTypeResponse updateMachineType(String machineTypeId, MachineTypeCreationRequest request);

  void updateQrCodeForMachine(String guidelineCode, Machine machineToUpdate);

  CourseResponse createGuideline(CourseCreationRequest request);
}
