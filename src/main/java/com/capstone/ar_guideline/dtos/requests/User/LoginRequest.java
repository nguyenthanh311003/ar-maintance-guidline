package com.capstone.ar_guideline.dtos.requests.User;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
