package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import java.util.List;

public interface ICompanyRequestService {
  PagingModel<CompanyRequestResponse> findAllForDesigner(
      int page, int size, String status, String companyName, String designerEmail);

  PagingModel<CompanyRequestResponse> findByCompanyId(
      int page, int size, String companyId, String status);

  PagingModel<CompanyRequestResponse> findAllForAdmin(
      int page, int size, String status, String companyName, String designerEmail);

  List<CompanyRequestResponse> findByDesignerId(String designerId);

  CompanyRequestResponse findById(String id);

  CompanyRequestResponse create(CompanyRequestCreation request);

  CompanyRequestResponse update(String requestId, CompanyRequestCreation request);

  void delete(Long id);
}
