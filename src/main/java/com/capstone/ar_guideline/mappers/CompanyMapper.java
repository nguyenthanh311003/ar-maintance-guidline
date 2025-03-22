package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.Company.CompanyCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponseManagement;
import com.capstone.ar_guideline.entities.Company;

public class CompanyMapper {
  public static Company fromCompanyCreationRequestToEntity(CompanyCreationRequest request) {
    return Company.builder().companyName(request.getCompanyName()).build();
  }

  public static CompanyResponse fromEntityToCompanyResponse(Company company) {
    return CompanyResponse.builder()
        .id(company.getId())
        .companyName(company.getCompanyName())
        .build();
  }

  public static CompanyResponseManagement fromEntityToCompanyResponseManagement(Company company) {
    return CompanyResponseManagement.builder()
        .id(company.getId())
        .companyName(company.getCompanyName())
        .createdDate(company.getCreatedDate())
        .build();
  }

  public static Company fromCompanyResponseToEntity(CompanyResponse response) {
    return Company.builder().id(response.getId()).companyName(response.getCompanyName()).build();
  }
}
