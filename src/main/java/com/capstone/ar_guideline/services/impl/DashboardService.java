package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Dashboard.AdminDashboardResponse;
import com.capstone.ar_guideline.dtos.responses.Dashboard.CompanyDashboardResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.repositories.*;

import java.math.BigDecimal;
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

  @Autowired private PointOptionsRepository pointOptionRepository;

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

    Long totalRevenue = 0l;

    // Get company revenue data
    List<Object[]> companiesWithRevenue =
        orderTransactionRepository.getCompaniesWithTotalPaidOrders();
    List<AdminDashboardResponse.CompanyRevenue> companyRevenueList = new ArrayList<>();

for (Object[] result : companiesWithRevenue) {
  Company company = result[0] != null ? (Company) result[0] : null;
  Long revenue = result[1] != null ? (Long) result[1] : 0;

  AdminDashboardResponse.CompanyRevenue companyRevenue =
      new AdminDashboardResponse.CompanyRevenue();
  companyRevenue.setName(company != null ? company.getCompanyName() : "Unknown");
  companyRevenue.setRevenue(revenue);

  companyRevenueList.add(companyRevenue);
}

    int currentYear = java.time.Year.now().getValue();
    List<Object[]> monthlyRevenues =
        orderTransactionRepository.getMonthlyPaidOrderAmounts(currentYear);
    List<AdminDashboardResponse.MonthRevenue> monthRevenueList = new ArrayList<>();

for (Object[] result : monthlyRevenues) {
  Long month = result[0] != null ? (Long)result[0] : 0L;
  Long revenue = result[1] != null ? Long.parseLong(((BigDecimal) result[1]).toString()) : 0L;

  AdminDashboardResponse.MonthRevenue monthRevenue = new AdminDashboardResponse.MonthRevenue();
  monthRevenue.setMonth(month);
  monthRevenue.setRevenue(revenue);
  totalRevenue = totalRevenue + revenue;
  monthRevenueList.add(monthRevenue);
}

List<Object[]> pointOptionRevenues = pointOptionRepository.findAllOptionNamesWithTotalAmount();
List<AdminDashboardResponse.PointOptionRevenue> pointOptionRevenueList = new ArrayList<>();

for (Object[] result : pointOptionRevenues) {
    String name = result[0] != null ? (String) result[0] : "Unknown";
    Long revenue = result[1] != null ? (Long)result[1]: 0L;

    AdminDashboardResponse.PointOptionRevenue pointOptionRevenue =
        new AdminDashboardResponse.PointOptionRevenue();
    pointOptionRevenue.setName(name);
    pointOptionRevenue.setRevenue(revenue);

    pointOptionRevenueList.add(pointOptionRevenue);
}
pointOptionRevenueList.sort((a, b) -> b.getRevenue().compareTo(a.getRevenue()));

    Pageable topThree = PageRequest.of(0, 3);
    List<Course> top3Courses = courseRepository.findTop3CoursesByScanTimes(topThree, null);
    List<CompanyDashboardResponse.Top3Guidelines> top3GuidelinesList = new ArrayList<>();

    for (Course course : top3Courses) {
      CompanyDashboardResponse.Top3Guidelines guideline =
              new CompanyDashboardResponse.Top3Guidelines();
      guideline.setName(course.getTitle());
      guideline.setScanTimes(course.getNumberOfScan());
      top3GuidelinesList.add(guideline);
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
            .pointOptionRevenueList(pointOptionRevenueList)
        .top3Guidelines(top3GuidelinesList)
        .numberOfActiveModels(numberOfActiveModels)
        .monthRevenueList(monthRevenueList)
        .totalRevenue(totalRevenue)
        .companyRevenueList(companyRevenueList)

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
    List<Course> top3Courses = courseRepository.findTop3CoursesByScanTimes(topThree, companyId);
    List<CompanyDashboardResponse.Top3Guidelines> top3GuidelinesList = new ArrayList<>();

    for (Course course : top3Courses) {
      CompanyDashboardResponse.Top3Guidelines guideline =
          new CompanyDashboardResponse.Top3Guidelines();
      guideline.setName(course.getTitle());
      guideline.setScanTimes(course.getNumberOfScan());
      top3GuidelinesList.add(guideline);
    }
top3GuidelinesList.sort((a, b) -> b.getScanTimes().compareTo(a.getScanTimes()));
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
