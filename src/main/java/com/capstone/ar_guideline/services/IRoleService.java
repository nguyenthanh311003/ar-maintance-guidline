package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Role.RoleCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Role.RoleResponse;
import java.util.List;

public interface IRoleService {
  List<RoleResponse> findAll();

  RoleResponse findById(String id);

  RoleResponse findByName(String name);

  RoleResponse create(RoleCreationRequest request);

  RoleResponse update(String id, RoleCreationRequest request);

  void delete(String id);
}
