package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Instruction;
import com.capstone.ar_guideline.entities.InstructionDetail;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CourseMapper;
import com.capstone.ar_guideline.mappers.InstructionMapper;
import com.capstone.ar_guideline.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ARGuidelineServiceImpl implements IARGuidelineService {
    IInstructionService instructionService;
    IInstructionDetailService instructionDetailService;
    ICourseService courseService;

    @Override
    public InstructionResponse createInstruction(InstructionCreationRequest request) {
        try {
            Course course = courseService.findById(request.getCourseId());
            List<Float> translations = request.getGuideViewPosition().getTranslation();
            List<Float> rotations = request.getGuideViewPosition().getRotation();
            Instruction newInstruction =
                    InstructionMapper.fromInstructionCreationRequestToEntity(request);
            Integer highestOrderNumber = instructionService.getHighestOrderNumber(course.getId());

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
    public PagingModel<InstructionResponse> getInstructionsByCourseId(int page, int size, String courseId) {
        try {
            PagingModel<InstructionResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Instruction> instructions = instructionService.findByCourseIdPaging(pageable, courseId);

            List<InstructionResponse> instructionResponses =
                    instructions.stream().map(
                            i -> {
                                InstructionResponse instructionResponse =
                                        InstructionMapper.fromEntityToInstructionResponse(i);
                                List<InstructionDetailResponse> instructionDetailResponses =
                                        instructionDetailService.findByInstructionId(i.getId())
                                                .stream()
                                                .map(ide -> InstructionDetailResponse.builder()
                                                        .id(ide.getId())
                                                        .instructionId(ide.getInstruction().getId())
                                                        .orderNumber(ide.getOrderNumber())
                                                        .description(ide.getDescription())
                                                        .imgString(ide.getImgUrl())
                                                        .name(ide.getName())
                                                        .fileString(ide.getFile())
                                                        .build()).toList();
                                instructionResponse.setInstructionDetailResponse(instructionDetailResponses);
                                return instructionResponse;
                            }
                    ).toList();
            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) instructions.getTotalElements());
            pagingModel.setTotalPages(instructions.getTotalPages());
            pagingModel.setObjectList(instructionResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED);
        }
    }

    @Override
    public CourseResponse findCourseById(String modelId) {
        try {
            Course course = courseService.findById(modelId);

            return CourseMapper.fromEntityToCourseResponse(course);

        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
        }
    }
}
