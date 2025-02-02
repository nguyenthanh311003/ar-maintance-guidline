package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.LessonDetail.LessonDetailCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import com.capstone.ar_guideline.services.ILessonDetailService;
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
public class LessonDetailController {

    @Autowired
    ILessonDetailService lessonDetailService;

    @GetMapping(value = ConstAPI.LessonDetailAPI.LESSON_DETAIL+"/lesson/{lessonId}")
    public ApiResponse<List<LessonDetailResponse>> getAllByLessonId(
            @PathVariable String lessonId) {
        log.info("get all by lesson id: {}", lessonId);
        return ApiResponse.<List<LessonDetailResponse>>builder().result(lessonDetailService.findAllByLessonId(lessonId)).build();
    }

    @PostMapping(value = ConstAPI.LessonDetailAPI.LESSON_DETAIL)
    public ApiResponse<LessonDetailResponse> createLessonDetail(
            @RequestBody @Valid LessonDetailCreationRequest request) {
        log.info("Creating a new lesson detail: {}", request);
        return ApiResponse.<LessonDetailResponse>builder().result(lessonDetailService.create(request)).build();
    }

    @PutMapping(value = ConstAPI.LessonDetailAPI.LESSON_DETAIL + "/{lessonDetailId}")
    public ApiResponse<LessonDetailResponse> updateLessonDetail(
            @PathVariable String lessonDetailId, @RequestBody @Valid LessonDetailCreationRequest request) {
        log.info("Updating lesson detail with ID: {}", lessonDetailId);
        return ApiResponse.<LessonDetailResponse>builder().result(lessonDetailService.update(lessonDetailId, request)).build();
    }

    @DeleteMapping(value = ConstAPI.LessonDetailAPI.LESSON_DETAIL + "/{lessonDetailId}")
    public ApiResponse<String> deleteLessonDetail(
            @PathVariable String lessonDetailId) {
        log.info("Deleting lesson detail with ID: {}", lessonDetailId);
        lessonDetailService.delete(lessonDetailId);
        return ApiResponse.<String>builder().result("Lesson detail has been deleted").build();
    }

    @PutMapping(value = ConstAPI.LessonDetailAPI.LESSON_DETAIL + "/swap")
    public ApiResponse<String> swapOrder(
            @RequestParam String id1, @RequestParam String id2) {
        log.info("Swapping order of lesson details with ID: {} and {}", id1, id2);
        lessonDetailService.swapOrder(id1, id2);
        return ApiResponse.<String>builder().result("Lesson details have been swapped").build();
    }
}
