package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.AssignGuideline.AssignGuidelineCreationRequest;
import com.capstone.ar_guideline.dtos.responses.AssignGuideline.AssignGuidelineResponse;
import java.util.List;

public interface IAssignGuidelineService {
  List<AssignGuidelineResponse> getAssignGuidelinesByGuidelineId(String guidelineId);

  AssignGuidelineResponse createAssignGuideline(AssignGuidelineCreationRequest request);
}
