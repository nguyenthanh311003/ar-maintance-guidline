package com.capstone.ar_guideline.dtos.requests.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
  private String userName;
  private String email;
  private String password;
  private String phone;
  private String avatar;
  private String company;
  private String status;
  private Long points;
  private String expirationDate;
  private Boolean isPayAdmin;
  private String roleName;
}
