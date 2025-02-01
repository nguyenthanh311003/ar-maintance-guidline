package com.capstone.ar_guideline.controllers;


import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.InstructionProcess.InstructionProcessCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionProcess.InstructionProcessResponse;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.services.IInstructionProcessService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InstructionProcessController {

    @Autowired
    IInstructionProcessService instructionProcessService;

    @PostMapping(value = ConstAPI.InstructionProcessAPI.InstructionProcess)
    public ApiResponse<InstructionProcessResponse> createLesson(
            @RequestBody @Valid InstructionProcessCreationRequest request) {
        log.info("Creating a new instruction process: {}", request);
        return ApiResponse.<InstructionProcessResponse>builder().result(instructionProcessService.createInstructionProcess(request)).build();
    }

    @GetMapping(value = ConstAPI.InstructionProcessAPI.InstructionProcess)
    public ApiResponse<List<InstructionProcessResponse>> createLesson( @RequestParam(required = true) String userId,
                                                                       @RequestParam(required = true) String courseId) {
        return ApiResponse.<List<InstructionProcessResponse>>builder().result(instructionProcessService.getInstructionProcessesByUserId(userId,courseId)).build();
    }
}
