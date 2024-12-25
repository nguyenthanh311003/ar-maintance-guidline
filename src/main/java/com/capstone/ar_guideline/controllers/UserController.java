package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
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
  ApiResponse<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      return ApiResponse.<AuthenticationResponse>builder()
          .message("Login process")
          .result(userService.login(loginRequest))
          .build();
    } catch (AuthenticationException exception) {
      log.error("Login failed: {}", exception.getMessage());
      return ApiResponse.<AuthenticationResponse>builder().message("Login failed").build();
    }
  }

  @PostMapping(value = ConstAPI.UserAPI.REGISTER)
  ApiResponse<AuthenticationResponse> register(@RequestBody SignUpRequest signUpRequest) {
    try {
      return ApiResponse.<AuthenticationResponse>builder()
          .message("Register process")
          .result(userService.create(signUpRequest))
          .build();
    } catch (AuthenticationException exception) {
      log.error("Register failed: {}", exception.getMessage());
      return ApiResponse.<AuthenticationResponse>builder().message("Register failed").build();
    }
  }
}
