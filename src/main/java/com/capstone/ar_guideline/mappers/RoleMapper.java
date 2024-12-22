package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Role.RoleCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Role.RoleResponse;
import com.capstone.ar_guideline.entities.Role;

public class RoleMapper {
  public static Role fromRoleCreationRequestToEntity(RoleCreationRequest request) {
    return Role.builder().roleName(request.getRoleName()).build();
  }

  public static RoleResponse fromEntityToRoleResponse(Role role) {
    return RoleResponse.builder().id(role.getId()).roleName(role.getRoleName()).build();
  }

  public static Role fromRoleResponseToEntity(RoleResponse response) {
    return Role.builder().roleName(response.getRoleName()).build();
  }
}
