package com.capstone.ar_guideline.repositories;

import com.capstone.ar_guideline.entities.CompanySubscription;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


  @Query("SELECT cs FROM CompanySubscription cs WHERE cs.subscriptionExpireDate <= :expireDate AND cs.status ='ACTIVE' ")
  List<CompanySubscription> findBySubscriptionExpireDateThatOnlyHave3DaysLeft(LocalDateTime expireDate);
}
