package com.capstone.ar_guideline.dtos.requests.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
  private String email;
  private String password;
  private String deviceId;
}
