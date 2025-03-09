package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Dashboard.AdminDashboardResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.repositories.ModelRepository;
import com.capstone.ar_guideline.repositories.OrderTransactionRepository;
import com.capstone.ar_guideline.repositories.SubscriptionRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired private UserRepository userRepository;

  @Autowired private ModelRepository modelRepository;

  @Autowired private CourseRepository courseRepository;

  @Autowired private OrderTransactionRepository orderTransactionRepository;

  @Autowired private SubscriptionRepository subscriptionRepository;

  public AdminDashboardResponse getAdminDashboard() {
    // Get active and inactive guidelines (courses)
    Integer numberOfGuidelines = courseRepository.countAllBy(null, null);
    Integer activeGuidelines = courseRepository.countAllBy(null, "ACTIVE");
    Integer inactiveGuidelines = courseRepository.countAllBy(null, "INACTIVE");

    // Get total number of accounts
    Integer numberOfAccount = userRepository.countAllBy(null, null);

    Integer numberOfActiveAccount = userRepository.countAllBy(null, "ACTIVE");

    Integer numberOfInactiveAccount = userRepository.countAllBy(null, "INACTIVE");

    // Get total number of models
    Integer numberOfModels = modelRepository.countAllBy(null, null);

    Integer numberOfActiveModels = modelRepository.countAllBy(null, "ACTIVE");

    Integer numberOfInactiveModels = modelRepository.countAllBy(null, "INACTIVE");

    // Get company revenue data
    List<Object[]> companiesWithRevenue =
        orderTransactionRepository.getCompaniesWithTotalPaidOrders();
    List<AdminDashboardResponse.CompanyRevenue> companyRevenueList = new ArrayList<>();

    for (Object[] result : companiesWithRevenue) {
      Company company = (Company) result[0];
      Double revenue = (Double) result[1];

      AdminDashboardResponse.CompanyRevenue companyRevenue =
          new AdminDashboardResponse.CompanyRevenue();
      companyRevenue.setName(company.getCompanyName());
      companyRevenue.setRevenue(revenue);

      companyRevenueList.add(companyRevenue);
    }

    // Get subscription revenue data
    List<Object[]> subscriptionsWithRevenue =
        orderTransactionRepository.getSubcriptionWithTotalPaidOrders();
    List<AdminDashboardResponse.SubscriptionRevenue> subscriptionRevenueList = new ArrayList<>();

    for (Object[] result : subscriptionsWithRevenue) {
      Subscription subscription = (Subscription) result[0];
      Double revenue = (Double) result[1];

      AdminDashboardResponse.SubscriptionRevenue subcriptionRevenue =
          new AdminDashboardResponse.SubscriptionRevenue();
      subcriptionRevenue.setName(subscription.getSubscriptionCode());
      subcriptionRevenue.setRevenue(revenue);

      subscriptionRevenueList.add(subcriptionRevenue);
    }

    // Build and return the response
    return AdminDashboardResponse.builder()
        .numberOfGuidelines(numberOfGuidelines)
        .numberOfActiveGuidelines(activeGuidelines)
        .numberOfInactiveGuidelines(inactiveGuidelines)
        .numberOfAccount(numberOfAccount)
        .numberOfActiveAccount(numberOfInactiveAccount)
        .numberOfInactiveAccount(numberOfInactiveAccount)
        .numberOfModels(numberOfModels)
        .companyRevenueList(companyRevenueList)
        .subscriptionRevenueList(subscriptionRevenueList)
        .build();
  }
}
