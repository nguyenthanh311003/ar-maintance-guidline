package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Role.RoleCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Role.RoleResponse;
import com.capstone.ar_guideline.services.IRoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
  IRoleService roleService;

  @GetMapping(value = ConstAPI.RoleAPI.GET_ROLES)
  ApiResponse<List<RoleResponse>> getRoles() {
    return ApiResponse.<List<RoleResponse>>builder()
        .result(roleService.findAll())
        .message("Get all roles successfully")
        .build();
  }

  @GetMapping(value = ConstAPI.RoleAPI.GET_ROLE_BY_ID)
  ApiResponse<RoleResponse> getRoleById(@RequestParam String id) {
    return ApiResponse.<RoleResponse>builder()
        .result(roleService.findById(id))
        .message("Get role by id successfully")
        .build();
  }

  @GetMapping(value = ConstAPI.RoleAPI.GET_ROLE_BY_NAME)
  ApiResponse<RoleResponse> getRoleByName(@RequestParam String name) {
    return ApiResponse.<RoleResponse>builder()
        .result(roleService.findByName(name))
        .message("Get role by name successfully")
        .build();
  }

  @PostMapping(value = ConstAPI.RoleAPI.CREATE_ROLE)
  ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleCreationRequest request) {
    return ApiResponse.<RoleResponse>builder()
        .result(roleService.create(request))
        .message("Create role successfully")
        .build();
  }
}
