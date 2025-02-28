package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.services.IARGuidelineService;
import com.capstone.ar_guideline.services.IInstructionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InstructionController {
  IInstructionService instructionService;
  IARGuidelineService arGuidelineService;

  @GetMapping(value = ConstAPI.InstructionAPI.GET_INSTRUCTIONS_BY_COURSE_ID + "{courseId}")
  ApiResponse<PagingModel<InstructionResponse>> getInstructions(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable String courseId) {
    return ApiResponse.<PagingModel<InstructionResponse>>builder()
        .result(arGuidelineService.getInstructionsByCourseId(page, size, courseId))
        .build();
  }

  @GetMapping(
      value = ConstAPI.InstructionAPI.GET_INSTRUCTIONS_BY_COURSE_ID_FOR_SWAP_ORDER + "{courseId}")
  ApiResponse<List<InstructionResponse>> getInstructionsByCourseForSwapOrder(
      @PathVariable String courseId) {
    return ApiResponse.<List<InstructionResponse>>builder()
        .result(instructionService.findByCourseId(courseId))
        .build();
  }

  @GetMapping(value = ConstAPI.InstructionAPI.GET_INSTRUCTION_BY_ID + "{instructionId}")
  ApiResponse<InstructionResponse> getById(@PathVariable String instructionId) {
    return ApiResponse.<InstructionResponse>builder()
        .result(instructionService.findByIdReturnResponse(instructionId))
        .build();
  }

  @PostMapping(value = ConstAPI.InstructionAPI.CREATE_INSTRUCTION)
  ApiResponse<InstructionResponse> createInstruction(
      @ModelAttribute @Valid InstructionCreationRequest request) {
    return ApiResponse.<InstructionResponse>builder()
        .result(arGuidelineService.createInstruction(request))
        .build();
  }

  @PutMapping(value = ConstAPI.InstructionAPI.UPDATE_INSTRUCTION + "{instructionId}")
  ApiResponse<InstructionResponse> updateInstruction(
      @PathVariable String instructionId,
      @ModelAttribute @Valid InstructionCreationRequest request) {
    return ApiResponse.<InstructionResponse>builder()
        .result(instructionService.update(instructionId, request))
        .build();
  }

  @PutMapping(value = ConstAPI.InstructionAPI.SWAP_ORDER_INSTRUCTION)
  ApiResponse<Boolean> swapOrder(
      @RequestParam String instructionIdCurrent, @RequestParam String instructionIdSwap) {
    return ApiResponse.<Boolean>builder()
        .result(instructionService.swapOrder(instructionIdCurrent, instructionIdSwap))
        .build();
  }

  @DeleteMapping(value = ConstAPI.InstructionAPI.DELETE_INSTRUCTION + "{instructionId}")
  ApiResponse<String> deleteInstruction(@PathVariable String instructionId) {
    arGuidelineService.deleteInstructionById(instructionId);
    return ApiResponse.<String>builder().result("Instruction has been deleted").build();
  }
}
