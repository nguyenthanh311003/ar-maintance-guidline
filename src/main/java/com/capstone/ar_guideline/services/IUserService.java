package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;

public interface IUserService {
  AuthenticationResponse login(LoginRequest loginRequest);

  <T> AuthenticationResponse create(SignUpRequest signUpRequest);
}
