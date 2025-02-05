package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.services.IOptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OptionController {

  IOptionService optionService;

  //  @PostMapping(value = ConstAPI.OptionAPI.OPTION)
  //  ApiResponse<OptionResponse> create(@RequestBody @Valid OptionCreationRequest request) {
  //    return ApiResponse.<OptionResponse>builder()
  //        .result(optionService.createOption(request))
  //        .build();
  //  }
}
