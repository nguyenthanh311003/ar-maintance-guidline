package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Role.RoleCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Role.RoleResponse;
import com.capstone.ar_guideline.entities.Role;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.RoleMapper;
import com.capstone.ar_guideline.repositories.RoleRepository;
import com.capstone.ar_guideline.services.IRoleService;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements IRoleService {
  RoleRepository roleRepository;
  RedisTemplate<String, Object> redisTemplate;

  @Override
  public List<RoleResponse> findAll() {
    try {
      List<Role> roles = roleRepository.findAll();
      return roles.stream().map(RoleMapper::fromEntityToRoleResponse).toList();
    } catch (Exception exception) {
      log.error("Role find all failed: {}", exception.getMessage());
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ROLE_FIND_ALL_FAILED);
    }
  }

  @Override
  public RoleResponse create(RoleCreationRequest request) {
    try {
      Role newRole = RoleMapper.fromRoleCreationRequestToEntity(request);
      newRole = roleRepository.save(newRole);
      redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_ROLE, newRole.getId(), newRole);
      return RoleMapper.fromEntityToRoleResponse(newRole);
    } catch (Exception exception) {
      log.error("Role create failed: {}", exception.getMessage());
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
    }
  }

  @Override
  public RoleResponse update(String id, RoleCreationRequest request) {
    Role roleByIdWithRedis = (Role) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_ROLE, id);

    if (!Objects.isNull(roleByIdWithRedis)) {
      roleByIdWithRedis.setRoleName(request.getRoleName());
      redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_ROLE, id, roleByIdWithRedis);
      roleRepository.save(roleByIdWithRedis);
      return RoleMapper.fromEntityToRoleResponse(roleByIdWithRedis);
    }

    Role roleById = RoleMapper.fromRoleResponseToEntity(findById(id));

    roleById.setRoleName(request.getRoleName());
    roleById = roleRepository.save(roleById);
    return RoleMapper.fromEntityToRoleResponse(roleById);
  }

  @Override
  public void delete(String id) {
    Role roleById = RoleMapper.fromRoleResponseToEntity(findById(id));
    redisTemplate.opsForHash().delete(ConstHashKey.HASH_KEY_ROLE, id);
    roleRepository.deleteById(roleById.getId());
  }

  @Override
  public RoleResponse findById(String id) {
    Role roleByIdWithRedis = (Role) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_ROLE, id);

    if (!Objects.isNull(roleByIdWithRedis)) {
      return RoleMapper.fromEntityToRoleResponse(roleByIdWithRedis);
    }

    Role roleById =
        roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    return RoleMapper.fromEntityToRoleResponse(roleById);
  }

  @Override
  public RoleResponse findByName(String name) {
    Role roleByNameWithRedis =
        (Role) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_ROLE, name);

    if (!Objects.isNull(roleByNameWithRedis)) {
      return RoleMapper.fromEntityToRoleResponse(roleByNameWithRedis);
    }

    Role roleByName =
        roleRepository
            .findByRoleName(name)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    return RoleMapper.fromEntityToRoleResponse(roleByName);
  }

  @Override
  public Role findRoleEntityByName(String name) {
    Role roleByName =
        roleRepository
            .findByRoleName(name)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    return roleByName;
  }
}
