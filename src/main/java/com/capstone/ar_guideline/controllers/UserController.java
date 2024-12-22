package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.User.LoginResponse;
import com.capstone.ar_guideline.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
  IUserService userService;

  @PostMapping(value = ConstAPI.UserAPI.LOGIN)
  ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      return ApiResponse.<LoginResponse>builder()
          .message("Login successfully")
          .result(userService.login(loginRequest))
          .build();
    } catch (AuthenticationException exception) {
      log.error("Login failed: {}", exception.getMessage());
      return ApiResponse.<LoginResponse>builder().message("Login failed").build();
    }
  }
}
