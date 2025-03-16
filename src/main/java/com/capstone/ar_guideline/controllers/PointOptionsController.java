package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.entities.PointOptions;
import com.capstone.ar_guideline.services.impl.PointOptionsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/point-options")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PointOptionsController {

    PointOptionsService pointOptionsService;

    @PostMapping
    public ApiResponse<PointOptions> createPointOptions(@RequestBody PointOptions pointOptions) {
        PointOptions createdPointOptions = pointOptionsService.create(pointOptions);
        return ApiResponse.<PointOptions>builder()
                .result(createdPointOptions)
                .message("PointOptions created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PointOptions> updatePointOptions(@PathVariable String id, @RequestBody PointOptions pointOptionsDetails) {
        PointOptions updatedPointOptions = pointOptionsService.update(id, pointOptionsDetails);
        return ApiResponse.<PointOptions>builder()
                .result(updatedPointOptions)
                .message("PointOptions updated successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<PointOptions>> getAllPointOptions() {
        List<PointOptions> pointOptionsList = pointOptionsService.getAll();
        return ApiResponse.<List<PointOptions>>builder()
                .result(pointOptionsList)
                .message("All PointOptions retrieved successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePointOptions(@PathVariable String id) {
        pointOptionsService.delete(id);
        return ApiResponse.<Void>builder()
                .message("PointOptions deleted successfully")
                .build();
    }
}