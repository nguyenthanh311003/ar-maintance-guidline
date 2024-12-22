package com.capstone.ar_guideline.dtos.responses.User;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse implements Serializable {
  private String token;
  private String message;
}
