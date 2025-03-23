package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.User;

public class UserMapper {

  public static User fromSignUpRequestToEntity(Object signUpWitRoleRequest) {
    // Assuming signUpWitRoleRequest is of a type that has the necessary fields
    // You may need to cast it to the appropriate type and extract the fields accordingly
    // For example, if signUpWitRoleRequest is of type SignUpRequest, cast it and extract fields

    SignUpRequest request = (SignUpRequest) signUpWitRoleRequest;

    return User.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .username(request.getEmail())
        .phone(request.getPhone())
        .avatar(request.getAvatar())
        .status(request.getStatus())
        .expirationDate(request.getExpirationDate())
        .isPayAdmin(request.getIsPayAdmin())
        .build();
  }

  public static UserResponse fromEntityToUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(RoleMapper.fromEntityToRoleResponse(user.getRole()))
        .avatar(user.getAvatar())
        .roleName(user.getRole().getRoleName())
        .company(
            user.getCompany() != null
                ? CompanyMapper.fromEntityToCompanyResponse(user.getCompany())
                : null)
        .isPayAdmin(user.getIsPayAdmin())
            .points(user.getWallet() != null ? user.getWallet().getBalance() : null)
        .status(user.getStatus())
        .deviceId(user.getDeviceId())
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
        .role(RoleMapper.fromRoleResponseToEntity(response.getRole()))
        .avatar(response.getAvatar())
        .company(CompanyMapper.fromCompanyResponseToEntity(response.getCompany()))
        .isPayAdmin(response.getIsPayAdmin())
        .status(response.getStatus())
        .username(response.getUsername())
        .phone(response.getPhone())
        .expirationDate(response.getExpirationDate())
        .createdDate(response.getCreatedDate())
        .build();
  }
}
