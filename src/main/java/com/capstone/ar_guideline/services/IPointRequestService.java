package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.PointRequestResponse.PointRequestCreation;
import com.capstone.ar_guideline.dtos.responses.PointRequestResponse.PointRequestResponse;
import java.util.List;

public interface IPointRequestService {
  List<PointRequestResponse> findAll();

  List<PointRequestResponse> findAllByCompanyId(String companyId);

  List<PointRequestResponse> findAllByEmployeeId(String employeeId);

  PointRequestResponse create(PointRequestCreation pointRequest);

  PointRequestResponse update(String requestId, PointRequestCreation request);
}
