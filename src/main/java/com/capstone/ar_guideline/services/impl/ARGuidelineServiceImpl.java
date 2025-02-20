package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.Model;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionMapper;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IInstructionDetailService;
import com.capstone.ar_guideline.services.IInstructionService;
import com.capstone.ar_guideline.services.IModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ARGuidelineServiceImpl implements IARGuidelineService {
  IInstructionService instructionService;
  IInstructionDetailService instructionDetailService;
  IModelService modelService;

  @Override
  public InstructionResponse createInstruction(InstructionCreationRequest request) {
    try {
      Model modelById = modelService.findById(request.getModelId());
      List<Float> translations = request.getGuideViewPosition().getTranslation();
      List<Float> rotations = request.getGuideViewPosition().getRotation();
      Instruction newInstruction =
          InstructionMapper.fromInstructionCreationRequestToEntity(request, modelById);
      Integer highestOrderNumber = instructionService.getHighestOrderNumber(modelById.getId());

      if (Objects.isNull(highestOrderNumber)) {
        newInstruction.setOrderNumber(1);
      } else {
        newInstruction.setOrderNumber(highestOrderNumber + 1);
      }

      String position =
          translations.stream().map(String::valueOf).collect(Collectors.joining(", "));
      String rotation = rotations.stream().map(String::valueOf).collect(Collectors.joining(", "));
      newInstruction.setPosition(position);
      newInstruction.setRotation(rotation);
      newInstruction = instructionService.create(newInstruction);

      if (newInstruction.getId() == null) {
        throw new AppException(ErrorCode.INSTRUCTION_CREATE_FAILED);
      }

      List<InstructionDetailResponse> instructionDetailResponses = new ArrayList<>();

      InstructionDetailResponse instructionDetailResponse =
          instructionDetailService.create(
              request.getInstructionDetailRequest(), newInstruction.getId());
      instructionDetailResponses.add(instructionDetailResponse);

      InstructionResponse instructionResponse =
          InstructionMapper.fromEntityToInstructionResponse(newInstruction);
      instructionResponse.setInstructionDetailResponse(instructionDetailResponses);
      return instructionResponse;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.INSTRUCTION_CREATE_FAILED);
    }
  }

  @Override
  public ModelResponse findModelById(String modelId) {
    try {
      Model modelById = modelService.findById(modelId);

      List<InstructionResponse> instructionResponses =
          instructionService.findByModelId(modelById.getId());
      return ModelResponse.builder()
          .id(modelById.getId())
          .modelTypeId(modelById.getModelType().getId())
          .modelCode(modelById.getModelCode())
          .status(modelById.getStatus())
          .name(modelById.getName())
          .description(modelById.getDescription())
          .imageUrl(modelById.getImageUrl())
          .version(modelById.getVersion())
          .scale(modelById.getScale())
          .file(modelById.getFile())
          .instructionResponses(instructionResponses)
          .build();

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }
}
