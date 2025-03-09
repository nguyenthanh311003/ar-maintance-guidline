package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.CompanySubscription;
import com.capstone.ar_guideline.entities.Subscription;

public class CompanySubscriptionMapper {
    public static CompanySubscription fromComSubscriptionCreationRequestToEntity(
            ComSubscriptionCreationRequest request, Company company, Subscription subscription) {
        return CompanySubscription.builder().company(company).subscription(subscription).build();
    }

    public static CompanySubscriptionResponse fromEntityToCompanySubscriptionResponse(
            CompanySubscription companySubscription) {
        return CompanySubscriptionResponse.builder()
                .id(companySubscription.getId())
                .companyId(companySubscription.getCompany().getId())
                .subscriptionId(companySubscription.getSubscription().getId())
                .subscriptionCode(companySubscription.getSubscription().getSubscriptionCode())
                .subscriptionStartDate(companySubscription.getSubscriptionStartDate())
                .subscriptionExpireDate(companySubscription.getSubscriptionExpireDate())
                .storageUsage(companySubscription.getStorageUsage())
                .numberOfUsers(companySubscription.getNumberOfUsers())
                .status(companySubscription.getStatus())
                .build();
    }
}
