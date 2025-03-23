package com.capstone.ar_guideline.dtos.responses.User;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.Role.RoleResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
  private String id;
  private RoleResponse role;
  private String roleName;
  private CompanyResponse company;
  private String email;
  private String currentPlan;
  private String avatar;
  private String username;
  private String deviceId;
  private String phone;
  private String status;
  private Long points;
  private String expirationDate;
  private Boolean isPayAdmin;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
}
