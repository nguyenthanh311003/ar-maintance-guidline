package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Dashboard.AdminDashboardResponse;
import com.capstone.ar_guideline.dtos.responses.Dashboard.CompanyDashboardResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.repositories.ModelRepository;
import com.capstone.ar_guideline.repositories.OrderTransactionRepository;
import com.capstone.ar_guideline.repositories.SubscriptionRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    Double totalRevenue = 0.0;

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

    int currentYear = java.time.Year.now().getValue();
    List<Object[]> monthlyRevenues =
        orderTransactionRepository.getMonthlyPaidOrderAmounts(currentYear);
    List<AdminDashboardResponse.MonthRevenue> monthRevenueList = new ArrayList<>();

    for (Object[] result : monthlyRevenues) {
      Long month = (Long) result[0];
      Double revenue = (Double) result[1];

      AdminDashboardResponse.MonthRevenue monthRevenue = new AdminDashboardResponse.MonthRevenue();
      monthRevenue.setMonth(month);
      monthRevenue.setRevenue(revenue);
      totalRevenue = totalRevenue + revenue;
      monthRevenueList.add(monthRevenue);
    }

    // Build and return the response
    return AdminDashboardResponse.builder()
        .numberOfGuidelines(numberOfGuidelines)
        .numberOfActiveGuidelines(activeGuidelines)
        .numberOfInactiveGuidelines(inactiveGuidelines)
        .numberOfAccount(numberOfAccount)
        .numberOfActiveAccount(numberOfActiveAccount)
        .numberOfInactiveAccount(numberOfInactiveAccount)
        .numberOfModels(numberOfModels)
        .numberOfInactiveModels(numberOfInactiveModels)
        .numberOfActiveModels(numberOfActiveModels)
        .monthRevenueList(monthRevenueList)
        .totalRevenue(totalRevenue)
        .companyRevenueList(companyRevenueList)
        .subscriptionRevenueList(subscriptionRevenueList)
        .build();
  }

  public CompanyDashboardResponse getCompanyDashboardById(String companyId) {
    // Get active and inactive guidelines (courses) for the company
    Integer numberOfGuidelines = courseRepository.countAllBy(companyId, null);
    Integer activeGuidelines = courseRepository.countAllBy(companyId, "ACTIVE");
    Integer inactiveGuidelines = courseRepository.countAllBy(companyId, "INACTIVE");

    // Get total number of accounts for the company
    Integer numberOfAccount = userRepository.countAllBy(companyId, null);
    Integer numberOfActiveAccount = userRepository.countAllBy(companyId, "ACTIVE");
    Integer numberOfInactiveAccount = userRepository.countAllBy(companyId, "INACTIVE");

    // Get total number of models for the company
    Integer numberOfModels = modelRepository.countAllBy(companyId, null);
    Integer numberOfActiveModels = modelRepository.countAllBy(companyId, "ACTIVE");
    Integer numberOfInactiveModels = modelRepository.countAllBy(companyId, "INACTIVE");

    // Get top 3 guidelines by scan times for the company
    Pageable topThree = PageRequest.of(0, 3);
    List<Course> top3Courses = courseRepository.findTop3CoursesByScanTimes(topThree);
    List<CompanyDashboardResponse.Top3Guidelines> top3GuidelinesList = new ArrayList<>();

    for (Course course : top3Courses) {
      CompanyDashboardResponse.Top3Guidelines guideline =
          new CompanyDashboardResponse.Top3Guidelines();
      guideline.setName(course.getTitle());
      guideline.setScanTimes(course.getNumberOfScan());
      top3GuidelinesList.add(guideline);
    }

    // Build and return the response
    return CompanyDashboardResponse.builder()
        .numberOfGuidelines(numberOfGuidelines)
        .numberOfActiveGuidelines(activeGuidelines)
        .numberOfInactiveGuidelines(inactiveGuidelines)
        .numberOfAccount(numberOfAccount)
        .numberOfActiveAccount(numberOfActiveAccount)
        .numberOfInactiveAccount(numberOfInactiveAccount)
        .numberOfModels(numberOfModels)
        .numberOfActiveModels(numberOfActiveModels)
        .numberOfInactiveModels(numberOfInactiveModels)
        .top3Guidelines(top3GuidelinesList)
        .build();
  }
}
