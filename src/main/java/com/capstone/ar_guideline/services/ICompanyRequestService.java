package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;

import java.util.List;

public interface ICompanyRequestService {
    List<CompanyRequestResponse> findAll();
    List<CompanyRequestResponse> findByCompanyId(String companyId);
    List<CompanyRequestResponse> findByDesignerId(String designerId);
    CompanyRequestResponse findById(String id);
    CompanyRequestResponse create(CompanyRequestCreation request);
    CompanyRequestResponse update(Long id, CompanyRequestCreation request);
    void delete(Long id);
}
