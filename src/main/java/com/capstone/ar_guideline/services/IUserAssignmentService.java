package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.responses.User.UserToAssignResponse;
import java.util.List;

public interface IUserAssignmentService {
  List<UserToAssignResponse> getUsersToAssign(String companyId, String courseId);
}
