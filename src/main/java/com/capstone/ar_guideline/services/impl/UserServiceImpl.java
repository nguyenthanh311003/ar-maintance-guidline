package com.capstone.ar_guideline.services.impl;

import static com.capstone.ar_guideline.constants.ConstStatus.INACTIVE_STATUS;

import com.capstone.ar_guideline.constants.ConstCommon;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Company.CompanyCreationRequest;
import com.capstone.ar_guideline.dtos.requests.User.LoginRequest;
import com.capstone.ar_guideline.dtos.requests.User.SignUpRequest;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.User.AuthenticationResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.CompanySubscription;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  AuthenticationManager authenticationManager;
  IJWTService jwtService;
  PasswordEncoder passwordEncoder;
  IRoleService roleService;
  ICompanyService companyService;
  EmailService emailService;
  ICompanySubscriptionService companySubscriptionService;

  @Override
  public AuthenticationResponse login(LoginRequest loginRequest) {
    try {
      UserResponse userResponseByEmail = getUserResponseByEmail(loginRequest.getEmail());
      if (Objects.isNull(userResponseByEmail)) {
        log.warn("User not found by email: {}", loginRequest.getEmail());
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
      }
      if (userResponseByEmail.getStatus().equals(INACTIVE_STATUS)) {
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
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
  }

  private void updateDeviceId() {}

  @Override
  public <T> AuthenticationResponse create(SignUpRequest signUpWitRoleRequest) {
    try {

      // Validate the role name
      var role = roleService.findRoleEntityByName(signUpWitRoleRequest.getRoleName());
      var company = companyService.findCompanyEntityByName(signUpWitRoleRequest.getCompany());
      CompanySubscription companySubscription =
          companySubscriptionService.findByCompanyId(company.getId());

      if (Objects.isNull(role)) {
        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
      }

      if (isUserOverSubscriptionLimit(company.getId())) {
        throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_USER_OVER_LIMIT);
      }

      var userByEmail = userRepository.findByEmail(signUpWitRoleRequest.getEmail());
      if (userByEmail.isPresent()) {
        throw new AppException(ErrorCode.USER_EXISTED);
      }

      User user = UserMapper.fromSignUpRequestToEntity(signUpWitRoleRequest);
      user.setRole(role);
      user.setCompany(company);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user = userRepository.save(user);

      companySubscriptionService.updateNumberOfUsers(company.getId(), ConstCommon.INCREASE);

      var jwt = jwtService.generateToken(user);
      UserResponse userResponse = UserMapper.fromEntityToUserResponse(user);

      return AuthenticationResponse.builder()
          .message("User created successfully")
          .token(jwt)
          .user(userResponse)
          .build();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
  }

  @Override
  public AuthenticationResponse createCompanyAccount(SignUpRequest signUpRequest) {
    try {
      var role = roleService.findRoleEntityByName(signUpRequest.getRoleName());

      if (Objects.isNull(role)) {
        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
      }

      CompanyCreationRequest companyCreationRequest =
          CompanyCreationRequest.builder().companyName(signUpRequest.getCompany()).build();
      Company newCompany = companyService.create(companyCreationRequest);
      User user = UserMapper.fromSignUpRequestToEntity(signUpRequest);
      user.setStatus(ConstStatus.PENDING);
      user.setRole(role);
      user.setCompany(newCompany);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user = userRepository.save(user);

      var jwt = jwtService.generateToken(user);
      UserResponse userResponse = UserMapper.fromEntityToUserResponse(user);

      return AuthenticationResponse.builder()
          .message("User created successfully")
          .token(jwt)
          .user(userResponse)
          .build();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_CREATE_FAILED);
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

  @Override
  public PagingModel<UserResponse> getUsers(int page, int size, String email, String status) {
    try {
      PagingModel<UserResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<User> users = userRepository.getUsers(pageable, email, status);

      List<UserResponse> userResponses =
          users.getContent().stream().map(UserMapper::fromEntityToUserResponse).toList();
      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) users.getTotalElements());
      pagingModel.setTotalPages(users.getTotalPages());
      pagingModel.setObjectList(userResponses);
      return pagingModel;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
  }

  @Override
  public UserResponse findByIdReturnUserResponse(String id) {
    try {
      User userById =
          userRepository
              .findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

      return UserMapper.fromEntityToUserResponse(userById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
  }

  @Override
  public Boolean changeStatus(String status, String userId, Boolean isPending) {
    try {
      User userById =
          userRepository
              .findById(userId)
              .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

      boolean isUserOver = isUserOverSubscriptionLimit(userById.getCompany().getId());
      if (isUserOver) {
        throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_MODEL_OVER_LIMIT);
      }

      if (status.isEmpty()) {
        return false;
      }

      userById.setStatus(status);
      userRepository.save(userById);

      if (status.equals(ConstStatus.ACTIVE_STATUS) && isPending) {
        emailService.sendAccountActivationEmail(userById.getEmail(), userById.getUsername());
      }

      if (status.equals(ConstStatus.REJECT) && isPending) {
        emailService.sendAccountRejectionEmail(userById.getEmail(), userById.getUsername());
      }
      return true;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_UPDATE_FAILED);
    }
  }

  @Override
  public Boolean changeStatusAccountStaff(String userId) {
    try {
      User userById =
          userRepository
              .findById(userId)
              .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

      boolean isUserOver = isUserOverSubscriptionLimit(userById.getCompany().getId());
      if (userById.getStatus().equalsIgnoreCase(INACTIVE_STATUS) && isUserOver) {
        throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_MODEL_OVER_LIMIT);
      }

      if (userById.getStatus().equalsIgnoreCase(INACTIVE_STATUS)) {
        userById.setStatus(ConstStatus.ACTIVE_STATUS);
        companySubscriptionService.updateNumberOfUsers(
            userById.getCompany().getId(), ConstCommon.INCREASE);
      } else {
        userById.setStatus(INACTIVE_STATUS);
        companySubscriptionService.updateNumberOfUsers(userById.getCompany().getId(), "");
      }
      userRepository.save(userById);

      return true;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_UPDATE_FAILED);
    }
  }

  @Override
  public Boolean resetPasswordStaff(String userId, String newPassword) {
    try {
      User userById =
          userRepository
              .findById(userId)
              .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

      boolean isUserOver = isUserOverSubscriptionLimit(userById.getCompany().getId());
      if (userById.getStatus().equalsIgnoreCase(INACTIVE_STATUS) && isUserOver) {
        throw new AppException(ErrorCode.COMPANY_SUBSCRIPTION_MODEL_OVER_LIMIT);
      }

      userById.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(userById);

      return true;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_UPDATE_FAILED);
    }
  }

  @Override
  public User findUserByCompanyId(String id) {
    return userRepository.findUserByCompanyId(id);
  }

  @Override
  public PagingModel<UserResponse> getStaffByCompanyId(
      int page, int size, String companyId, String username, String email, String status) {
    try {
      PagingModel<UserResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<User> users =
          userRepository.getStaffByCompanyId(pageable, companyId, username, email, status);

      List<UserResponse> userResponses =
          users.getContent().stream().map(UserMapper::fromEntityToUserResponse).toList();

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) users.getTotalElements());
      pagingModel.setTotalPages(users.getTotalPages());
      pagingModel.setObjectList(userResponses);
      return pagingModel;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
  }

  private boolean isUserOverSubscriptionLimit(String companyId) {
    CompanySubscription companySubscription = companySubscriptionService.findByCompanyId(companyId);
    return companySubscription.getNumberOfUsers()
        >= companySubscription.getSubscription().getMaxNumberOfUsers();
  }

  private List<User> findAllByCompanyId(String companyId) {
    return userRepository.findByCompanyId(companyId);
  }
}
