package com.capstone.ar_guideline.dtos.responses.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserToAssignResponse {
  UserResponse userResponse;
  Boolean isAssigned;
}
