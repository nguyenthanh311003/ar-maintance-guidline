package com.capstone.ar_guideline.dtos.responses.User;

import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Role;
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
  private Role role;
  private Company company;
  private String email;
  private String avatar;
  private String username;
  private String phone;
  private String status;
  private String expirationDate;
  private Boolean isPayAdmin;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
}
