package com.capstone.ar_guideline.dtos.requests.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreationRequest {
  private String email;
  private String avatar;
  private String password;
  private String phone;
  private String status;
  private String expirationDate;
  private Boolean isPayAdmin;
  private String roleId;
  private String companyId;
}
