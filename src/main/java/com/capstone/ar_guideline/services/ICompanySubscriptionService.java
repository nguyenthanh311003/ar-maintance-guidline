package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
import com.capstone.ar_guideline.entities.CompanySubscription;

public interface ICompanySubscriptionService {
  CompanySubscriptionResponse create(ComSubscriptionCreationRequest request);

  CompanySubscriptionResponse update(String id, ComSubscriptionCreationRequest request);

  void delete(String id);

  CompanySubscription findById(String id);

  CompanySubscriptionResponse disable(String id);

  CompanySubscription findByCompanyId(String companyId);

  CompanySubscription findCurrentSubscriptionByCompanyId(String companyId);

  void updateStorageUsage(String companyId, Double storageUsage, String action);

  void updateNumberOfUsers(String companyId, String action);

  CompanySubscriptionResponse findResponseById(String id);

}
