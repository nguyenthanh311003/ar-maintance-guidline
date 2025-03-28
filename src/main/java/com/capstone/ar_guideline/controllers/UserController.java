package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
  IUserService userService;

  //  @GetMapping(value = ConstAPI.UserAPI.PREFIX_USER + "company/{companyId}/" +
  // "course/{courseId}")
  //  public ApiResponse<PagingModel<UserToAssignResponse>> getUserToAssign(
  //      @RequestParam(defaultValue = "1") int page,
  //      @RequestParam(defaultValue = "10") int size,
  //      @RequestParam String keyword,
  //      @RequestParam(defaultValue = "") String isAssign,
  //      @PathVariable String companyId,
  //      @PathVariable String courseId) {
  //    return ApiResponse.<PagingModel<UserToAssignResponse>>builder()
  //        .result(
  //            userAssignmentService.getUsersToAssign(
  //                page, size, companyId, courseId, keyword, isAssign))
  //        .build();
  //  }

  @GetMapping(value = ConstAPI.UserAPI.GET_USERS)
  public ApiResponse<PagingModel<UserResponse>> getUsers(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") String email,
      @RequestParam(defaultValue = "") String status) {
    return ApiResponse.<PagingModel<UserResponse>>builder()
        .result(userService.getUsers(page, size, email, status))
        .build();
  }

  @GetMapping(value = ConstAPI.UserAPI.GET_STAFF_BY_COMPANY + "{companyId}")
  public ApiResponse<PagingModel<UserResponse>> getStaffByCompanyId(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") String phoneNumber,
      @RequestParam(defaultValue = "") String email,
      @RequestParam(defaultValue = "") String status,
      @PathVariable String companyId) {
    return ApiResponse.<PagingModel<UserResponse>>builder()
        .result(userService.getStaffByCompanyId(page, size, companyId, phoneNumber, email, status))
        .build();
  }

  @GetMapping(value = ConstAPI.UserAPI.PREFIX_USER + "{userId}")
  public ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.findByIdReturnUserResponse(userId))
        .build();
  }

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

  @PostMapping(value = ConstAPI.UserAPI.REGISTER_FOR_COMPANY)
  ApiResponse<AuthenticationResponse> registerForCompany(@RequestBody SignUpRequest signUpRequest) {
    try {
      return ApiResponse.<AuthenticationResponse>builder()
          .message("Register process")
          .result(userService.createCompanyAccount(signUpRequest))
          .build();
    } catch (AuthenticationException exception) {
      log.error("Register failed: {}", exception.getMessage());
      return ApiResponse.<AuthenticationResponse>builder().message("Register failed").build();
    }
  }

  @PutMapping(value = ConstAPI.UserAPI.PREFIX_USER + "{userId}")
  ApiResponse<Boolean> changeStatus(
      @PathVariable String userId,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) Boolean isPending) {
    return ApiResponse.<Boolean>builder()
        .result(userService.changeStatus(status, userId, isPending))
        .build();
  }

  @PutMapping(value = ConstAPI.UserAPI.PREFIX_USER + "change-status/{userId}")
  ApiResponse<Boolean> changeStatusAccountStaff(@PathVariable String userId) {
    return ApiResponse.<Boolean>builder()
        .result(userService.changeStatusAccountStaff(userId))
        .build();
  }

  @PutMapping(value = ConstAPI.UserAPI.PREFIX_USER + "reset/{userId}")
  ApiResponse<Boolean> resetPasswordStaff(
      @PathVariable String userId, @RequestBody String newPassword) {
    return ApiResponse.<Boolean>builder()
        .result(userService.resetPasswordStaff(userId, newPassword))
        .build();
  }

  @DeleteMapping(value = ConstAPI.UserAPI.PREFIX_USER + "{userId}")
  ApiResponse<Boolean> deleteUser(@PathVariable String userId) {
    return ApiResponse.<Boolean>builder().result(userService.deleteUser(userId)).build();
  }
}
