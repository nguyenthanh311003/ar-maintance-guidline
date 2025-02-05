package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.User.UserResponse;
import com.capstone.ar_guideline.dtos.responses.User.UserToAssignResponse;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.UserMapper;
import com.capstone.ar_guideline.services.IEnrollmentService;
import com.capstone.ar_guideline.services.IUserAssignmentService;
import com.capstone.ar_guideline.services.IUserService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserAssignmentService implements IUserAssignmentService {
  IUserService userService;
  IEnrollmentService enrollmentService;

  @Override
  public List<UserToAssignResponse> getUsersToAssign(String companyId, String courseId) {
    try {
      List<User> users = userService.getUserByCompanyId(companyId);

      return users.stream()
          .map(
              u -> {
                UserToAssignResponse userToAssignResponse = new UserToAssignResponse();
                UserResponse userResponse;
                userResponse = UserMapper.fromEntityToUserResponse(u);
                userToAssignResponse.setUserResponse(userResponse);
                boolean isAssign = enrollmentService.checkUserIsAssign(u.getId(), courseId);
                userToAssignResponse.setIsAssigned(isAssign);
                return userToAssignResponse;
              })
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.FIND_USER_TO_ASSIGN_FAILED);
    }
  }
}
