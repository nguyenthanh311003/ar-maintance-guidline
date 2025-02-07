package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.dtos.responses.User.UserToAssignResponse;

public interface IUserAssignmentService {
  PagingModel<UserToAssignResponse> getUsersToAssign(
      int page, int size, String companyId, String courseId, String keyword, String isAssign);
}
