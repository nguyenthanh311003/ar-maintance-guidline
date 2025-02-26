package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanySubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySubscriptionRepository extends JpaRepository<CompanySubscription, String> {
  CompanySubscription findByCompanyId(String companyId);

  CompanySubscription findByCompanyIdAndSubscriptionId(String companyId, String subscriptionId);
}
