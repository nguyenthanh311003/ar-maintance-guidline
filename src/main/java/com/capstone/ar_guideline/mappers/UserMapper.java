package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.User;

public class UserMapper {
  //    public static User fromModelTypeCreationRequestToEntity(ModelTypeCreationRequest request) {
  //        return ModelType.builder()
  //                .name(request.getName())
  //                .image(request.getImage())
  //                .description(request.getDescription())
  //                .build();
  //    }

  public static UserResponse fromEntityToUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .role(user.getRole())
        .avatar(user.getAvatar())
        .company(user.getCompany())
        .isPayAdmin(user.getIsPayAdmin())
        .status(user.getStatus())
        .username(user.getUsername())
        .phone(user.getPhone())
        .expirationDate(user.getExpirationDate())
        .createdDate(user.getCreatedDate())
        .build();
  }

  public static User fromUserResponseToEntity(UserResponse response) {
    return User.builder()
        .id(response.getId())
        .email(response.getEmail())
        .password(response.getPassword())
        .role(response.getRole())
        .avatar(response.getAvatar())
        .company(response.getCompany())
        .isPayAdmin(response.getIsPayAdmin())
        .status(response.getStatus())
        .username(response.getUsername())
        .phone(response.getPhone())
        .expirationDate(response.getExpirationDate())
        .createdDate(response.getCreatedDate())
        .build();
  }
}
