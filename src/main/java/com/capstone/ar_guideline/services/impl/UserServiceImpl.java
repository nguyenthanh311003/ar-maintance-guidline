package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.responses.User.LoginResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.UserMapper;
import com.capstone.ar_guideline.repositories.UserRepository;
import com.capstone.ar_guideline.services.IJWTService;
import com.capstone.ar_guideline.services.IUserService;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
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
      var jwt = jwtService.generateToken(userRepository.findByEmail(loginRequest.getEmail()).get());
      return LoginResponse.builder().message("Login successfully").token(jwt).build();
    } catch (Exception e) {
      log.error("Login failed: {}", e.getMessage());
      return LoginResponse.builder().message("Login failed").build();
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
      if (!userByEmail.isPresent()) {
        log.warn("User not found by email: {}", email);
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
      }
      return UserMapper.fromEntityToUserResponse(userByEmail.get());
    } catch (Exception e) {
      log.error("Error when get user by email: {}", e.getMessage());
    }
    return null;
  }
}
