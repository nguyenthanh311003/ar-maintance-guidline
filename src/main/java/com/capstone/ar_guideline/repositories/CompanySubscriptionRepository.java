package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanySubscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySubscriptionRepository extends JpaRepository<CompanySubscription, String> {
  CompanySubscription findByCompanyId(String companyId);

  CompanySubscription findByCompanyIdAndSubscriptionId(String companyId, String subscriptionId);

  @Query(
      value =
          "SELECT cs FROM CompanySubscription cs WHERE cs.company.id = :companyId "
              + "AND cs.status = 'ACTIVE' "
              + "AND cs.subscriptionExpireDate >= CURRENT_DATE"
              + " ORDER BY cs.startDate DESC")
  List<CompanySubscription> findCurrentSubscriptionByCompanyId(String companyId);
}
