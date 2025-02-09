package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
import com.capstone.ar_guideline.entities.User;
import java.util.List;

public interface IUserService {
  AuthenticationResponse login(LoginRequest loginRequest);

  <T> AuthenticationResponse create(SignUpRequest signUpRequest);

  User findById(String id);

  List<User> getUserByCompanyId(
      int page, int size, String companyId, String keyword, String isAssign, String courseId);

  int countUsersByCompanyId(String companyId, String keyword, String isAssign, String courseId);
}
