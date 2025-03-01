package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.User;
import java.util.List;

public interface IUserService {
  AuthenticationResponse login(LoginRequest loginRequest);

  <T> AuthenticationResponse create(SignUpRequest signUpRequest);

  AuthenticationResponse createCompanyAccount(SignUpRequest signUpRequest);

  User findById(String id);

  List<User> getUserByCompanyId(
      int page, int size, String companyId, String keyword, String isAssign, String courseId);

  int countUsersByCompanyId(String companyId, String keyword, String isAssign, String courseId);

  PagingModel<UserResponse> getUsers(int page, int size, String email, String status);

  UserResponse findByIdReturnUserResponse(String id);

  Boolean changeStatus(String status, String userId, Boolean isPending);

  PagingModel<UserResponse> getStaffByCompanyId(
      int page, int size, String companyId, String username, String email, String status);

  Boolean changeStatusAccountStaff(String userId);

  Boolean resetPasswordStaff(String userId, String newPassword);
}
