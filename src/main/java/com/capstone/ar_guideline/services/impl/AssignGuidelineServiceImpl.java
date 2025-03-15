package com.capstone.ar_guideline.services.impl;

import static com.capstone.ar_guideline.constants.ConstStatus.PROCESSING;

import com.capstone.ar_guideline.dtos.requests.AssignGuideline.AssignGuidelineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.AssignGuideline.AssignGuidelineResponse;
import com.capstone.ar_guideline.entities.AssignGuideline;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.AssignGuidelineMapper;
import com.capstone.ar_guideline.repositories.AssignGuideLineRepository;
import com.capstone.ar_guideline.services.IAssignGuidelineService;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignGuidelineServiceImpl implements IAssignGuidelineService {
  private final AssignGuideLineRepository assignGuideLineRepository;
  private final ICourseService courseService;
  private final IUserService userService;
  private final EmailService emailService;

  @Override
  public List<AssignGuidelineResponse> getAssignGuidelinesByGuidelineId(String guidelineId) {
    try {
      log.info("Get all assign guidelines by guideline id");

      return assignGuideLineRepository.findByGuidelineId(guidelineId).stream()
          .map(AssignGuidelineMapper::fromEntityToResponse)
          .toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.GET_ASSIGN_GUIDELINES_BY_GUIDELINE_ID_FAILED);
    }
  }

  public AssignGuidelineResponse createAssignGuideline(AssignGuidelineCreationRequest request) {
    try {
      // TODO: Must handle minus point here
      AssignGuideline assignGuideline = AssignGuidelineMapper.fromCreationRequestToEntity(request);
      assignGuideline.setEmployee(userService.findById(request.getEmployeeId()));
      assignGuideline.setManager(userService.findById(request.getManagerId()));
      assignGuideline.setGuideline(courseService.findById(request.getGuidelineId()));
      assignGuideline.setStatus(PROCESSING);
      assignGuideline = assignGuideLineRepository.save(assignGuideline);
      emailService.sendAssignGuidelineEmail(
          assignGuideline.getEmployee().getEmail(),
          assignGuideline.getManager().getEmail(),
          assignGuideline.getGuideline().getTitle());
      return AssignGuidelineMapper.fromEntityToResponse(assignGuideline);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.GET_ASSIGN_GUIDELINES_BY_GUIDELINE_ID_FAILED);
    }
  }
}
