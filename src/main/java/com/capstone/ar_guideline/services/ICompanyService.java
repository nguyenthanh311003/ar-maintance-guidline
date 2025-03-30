package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Company.CompanyCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponseManagement;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.Company;
import java.util.List;

public interface ICompanyService {
  List<CompanyResponse> findAll();

  CompanyResponse findById(String id);

  CompanyResponse findByName(String name);

  Company create(CompanyCreationRequest request);

  CompanyResponse update(String id, CompanyCreationRequest request);

  void delete(String id);

  Company findCompanyEntityByName(String name);

  Company findByIdReturnEntity(String id);

  CompanyResponse findByUserId(String userId);

  PagingModel<CompanyResponseManagement> findAllForManagement(
      int page, int size, String companyName);
}
