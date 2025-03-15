package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.AssignGuideline;
import com.capstone.ar_guideline.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignGuideLineRepository extends JpaRepository<AssignGuideline, String> {

  List<AssignGuideline> findByGuidelineId(String guidelineId);

  List<AssignGuideline> findByEmployeeId(String userId);

  List<AssignGuideline> findByGuidelineIdAndEmployeeId(String guidelineId, User employeeId);
}
