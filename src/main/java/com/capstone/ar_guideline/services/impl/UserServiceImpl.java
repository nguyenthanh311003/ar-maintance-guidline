package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.UserMapper;
import com.capstone.ar_guideline.repositories.UserRepository;
import com.capstone.ar_guideline.services.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements IUserService {
  UserRepository userRepository;
  RedisTemplate<String, Object> redisTemplate;
  AuthenticationManager authenticationManager;
  IJWTService jwtService;
  PasswordEncoder passwordEncoder;
  IRoleService roleService;
  ICompanyService companyService;

  @Override
  public AuthenticationResponse login(LoginRequest loginRequest) {
    try {
      UserResponse userResponseByEmail = getUserResponseByEmail(loginRequest.getEmail());
      if (Objects.isNull(userResponseByEmail)) {
        log.warn("User not found by email: {}", loginRequest.getEmail());
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
      }
      if (userResponseByEmail.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
        throw new AppException(ErrorCode.USER_DISABLED);
      }

      // Authenticate the user
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  userResponseByEmail.getEmail(), loginRequest.getPassword()));

      // Set the authentication in the SecurityContext
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserResponse userResponse = getUserResponseByEmail(loginRequest.getEmail());
      var jwt = jwtService.generateToken(userRepository.findByEmail(loginRequest.getEmail()).get());

      return AuthenticationResponse.builder()
          .message("Login successfully")
          .token(jwt)
          .user(userResponse)
          .build();
    } catch (Exception e) {
      log.error("Login failed: {}", e.getMessage());
      return AuthenticationResponse.builder().message("Login failed").build();
    }
  }

  @Override
  public <T> AuthenticationResponse create(SignUpRequest signUpWitRoleRequest) {
    try {
      // Validate the role name
      var role = roleService.findRoleEntityByName(signUpWitRoleRequest.getRoleName());
      var company = companyService.findCompanyEntityByName(signUpWitRoleRequest.getCompany());
      if (Objects.isNull(role)) {
        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
      }
      User user = UserMapper.fromSignUpRequestToEntity(signUpWitRoleRequest);
      user.setRole(role);
      user.setCompany(company);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user = userRepository.save(user);
      var jwt = jwtService.generateToken(user);
      UserResponse userResponse = UserMapper.fromEntityToUserResponse(user);

      return AuthenticationResponse.builder()
          .message("User created successfully")
          .token(jwt)
          .user(userResponse)
          .build();
    } catch (Exception e) {
      log.error("Error when create user: {}", e.getMessage());
      return AuthenticationResponse.builder().message("Create user failed").build();
    }
  }

  @Override
  public User findById(String id) {
    try {
      return userRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
  }

  @Override
  public List<User> getUserByCompanyId(
      int page, int size, String companyId, String keyword, String isAssign, String courseId) {
    try {
      Pageable pageable = PageRequest.of(page - 1, size);
      return userRepository.getUserByCompanyId(pageable, companyId, keyword, isAssign, courseId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
  }

  private UserResponse getUserResponseByEmail(String email) {
    try {
      UserResponse userByEmailWithRedis =
          (UserResponse) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_USER, email);
      if (!Objects.isNull(userByEmailWithRedis)) {
        return userByEmailWithRedis;
      }
      Optional<User> userByEmail = userRepository.findByEmail(email);
      if (userByEmail.isEmpty()) {
        log.warn("User not found by email: {}", email);
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
      }

      return UserMapper.fromEntityToUserResponse(userByEmail.get());
    } catch (Exception e) {
      log.error("Error when get user by email: {}", e.getMessage());
    }
    return null;
  }

  @Override
  public int countUsersByCompanyId(
      String companyId, String keyword, String isAssign, String courseId) {
    return userRepository.countUsersByCompanyId(companyId, keyword, isAssign, courseId);
  }
}
