package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.services.IInstructionDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InstructionDetailController {
  IInstructionDetailService instructionDetailService;

  /*@PostMapping(value = ConstAPI.InstructionDetailAPI.CREATE_INSTRUCTION_DETAIL)
  ApiResponse<InstructionDetailResponse> createInstructionDetail(
      @RequestBody @Valid InstructionDetailCreationRequest request) {
    return ApiResponse.<InstructionDetailResponse>builder()
        .result(instructionDetailService.create(request))
        .build();
  }*/

  @PutMapping(
      value = ConstAPI.InstructionDetailAPI.UPDATE_INSTRUCTION_DETAIL + "{instructionDetailId}")
  ApiResponse<InstructionDetailResponse> updateInstructionDetail(
      @PathVariable String instructionDetailId,
      @RequestBody InstructionDetailCreationRequest request) {
    return ApiResponse.<InstructionDetailResponse>builder()
        .result(instructionDetailService.update(instructionDetailId, request))
        .build();
  }

  @PutMapping(value = ConstAPI.InstructionDetailAPI.SWAP_ORDER_INSTRUCTION_DETAIL)
  ApiResponse<Boolean> swapOrder(
      @RequestParam String instructionDetailIdCurrent,
      @RequestParam String instructionDetailIdSwap) {
    return ApiResponse.<Boolean>builder()
        .result(
            instructionDetailService.swapOrder(instructionDetailIdCurrent, instructionDetailIdSwap))
        .build();
  }

  @DeleteMapping(
      value = ConstAPI.InstructionDetailAPI.DELETE_INSTRUCTION_DETAIL + "{instructionDetailId}")
  ApiResponse<String> deleteInstructionDetail(@PathVariable String instructionDetailId) {
    instructionDetailService.delete(instructionDetailId);
    return ApiResponse.<String>builder().result("Instruction detail has been deleted").build();
  }
}
