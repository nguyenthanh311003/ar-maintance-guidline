package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.responses.User.LoginResponse;

public interface IUserService {
  LoginResponse login(LoginRequest loginRequest);
}
