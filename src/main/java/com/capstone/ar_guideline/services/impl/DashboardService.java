package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.responses.Dashboard.AdminDashboardResponse;
import com.capstone.ar_guideline.dtos.responses.Dashboard.CompanyDashboardResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.repositories.*;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  @Autowired private MachineRepository machineRepository;

  @Autowired private MachineTypeRepository machineTypeRepository;

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
    Pageable topThree = PageRequest.of(0, 3);

    List<Object[]> companiesWithRevenue =
        orderTransactionRepository.getCompaniesWithTotalPaidOrders(topThree);
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
      Long month = result[0] != null ? (Long) result[0] : 0L;
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
      Long revenue = result[1] != null ? (Long) result[1] : 0L;

      AdminDashboardResponse.PointOptionRevenue pointOptionRevenue =
          new AdminDashboardResponse.PointOptionRevenue();
      pointOptionRevenue.setName(name);
      pointOptionRevenue.setRevenue(revenue);

      pointOptionRevenueList.add(pointOptionRevenue);
    }
    pointOptionRevenueList.sort((a, b) -> b.getRevenue().compareTo(a.getRevenue()));

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
        .top3Company(companyRevenueList)
        .build();
  }

  public CompanyDashboardResponse getCompanyDashboardById(String companyId) {
    // Get active and inactive guidelines (courses) for the company
    Integer numberOfGuidelines = courseRepository.countAllBy(companyId, null);
    Integer activeGuidelines = courseRepository.countAllBy(companyId, "ACTIVE");
    Integer inactiveGuidelines = courseRepository.countAllBy(companyId, "INACTIVE");

    // Get total number of accounts for the company
    Integer numberOfAccount = userRepository.countAllForCompanyBy(companyId, null);
    Integer numberOfActiveAccount = userRepository.countAllForCompanyBy(companyId, "ACTIVE");
    Integer numberOfInactiveAccount = userRepository.countAllForCompanyBy(companyId, "INACTIVE");

    // Get total number of models for the company
    Integer numberOfModels = modelRepository.countAllBy(companyId, null);
    Integer numberOfActiveModels = modelRepository.countAllBy(companyId, "ACTIVE");
    Integer numberOfInactiveModels = modelRepository.countAllBy(companyId, "INACTIVE");

    // Get total number of machines
    Integer numberOfMachines = machineRepository.countMachinesByCompanyId(companyId);

    if (numberOfMachines == null) {
      numberOfMachines = 0;
    }

    // Get total number of machines type
    Integer numberOfMachinesType = machineTypeRepository.countByCompany_Id(companyId);

    if (numberOfMachinesType == null) {
      numberOfMachinesType = 0;
    }

    // Get top 3 guidelines by scan times for the company
    Pageable topThree = PageRequest.of(0, 3);
    List<Course> top3Courses = courseRepository.findTop3CoursesByScanTimes(topThree, companyId);
    List<CompanyDashboardResponse.Top3Guidelines> top3GuidelinesList = new ArrayList<>();

    List<Object[]> monthlyVenue =
        walletTransactionRepository.findPointRequestTransactionsOverLast12Months(companyId);
    List<AdminDashboardResponse.MonthRevenue> monthRevenueList = new ArrayList<>();

    List<Object[]> top3Employee =
        walletTransactionRepository.findTop3UsersWithPointRequestTransactions(topThree, companyId);
    List<CompanyDashboardResponse.Top3Guidelines> top3EmployeesList = new ArrayList<>();

    for (Object[] result : top3Employee) {
      String username = result[0] != null ? (String) result[0] : "Unknown";
      Long transactionCount = result[1] != null ? (Long) result[1] : 0L;

      CompanyDashboardResponse.Top3Guidelines employee =
          new CompanyDashboardResponse.Top3Guidelines();
      employee.setName(username);
      employee.setScanTimes(transactionCount.intValue());
      top3EmployeesList.add(employee);
    }

    for (int i = 1; i <= 12; i++) {
      Long month = (long) i;
      Long transactionCount = 0L;

      for (Object[] result : monthlyVenue) {
        if (result[0] != null && ((Long) result[0]).intValue() == i) {
          transactionCount = result[1] != null ? (Long) result[1] : 0L;
          break;
        }
      }

      AdminDashboardResponse.MonthRevenue monthRevenue = new AdminDashboardResponse.MonthRevenue();
      monthRevenue.setMonth(month);
      monthRevenue.setRevenue(transactionCount);
      monthRevenueList.add(monthRevenue);
    }

    for (Course course : top3Courses) {
      CompanyDashboardResponse.Top3Guidelines guideline =
          new CompanyDashboardResponse.Top3Guidelines();
      guideline.setName(course.getTitle());
      guideline.setScanTimes(course.getNumberOfScan());
      top3GuidelinesList.add(guideline);
    }
    top3GuidelinesList.sort((a, b) -> b.getScanTimes().compareTo(a.getScanTimes()));

    // Thêm mới: Lấy dữ liệu về point purchases hàng tháng
    List<CompanyDashboardResponse.MonthlyPointPurchases> monthlyPointPurchasesList =
        new ArrayList<>();

    // Thêm mới: Lấy dữ liệu về point usage hàng tháng
    List<CompanyDashboardResponse.MonthlyPointUsage> monthlyPointUsageList = new ArrayList<>();

    // Lấy dữ liệu cho 12 tháng
    for (int i = 1; i <= 12; i++) {
      // Convert số tháng thành String để truyền vào query
      String monthStr = String.valueOf(i);

      // Gọi repository method để lấy tổng balance type CREDIT theo tháng và company
      Long pointPurchaseAmount =
          walletTransactionRepository.findTotalCreditBalanceByMonthAndCompany(monthStr, companyId);

      // Gọi repository method để lấy tổng balance type DEBIT theo tháng và company
      Long pointUsageAmount =
          walletTransactionRepository.findTotalDebitBalanceByMonthAndCompany(monthStr, companyId);

      // Tạo tên tháng (January, February, ...)
      String monthName = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

      // Tạo đối tượng MonthlyPointPurchases
      CompanyDashboardResponse.MonthlyPointPurchases monthlyPointPurchases =
          new CompanyDashboardResponse.MonthlyPointPurchases();
      monthlyPointPurchases.setMonth(monthName);
      monthlyPointPurchases.setAmount(pointPurchaseAmount);
      monthlyPointPurchasesList.add(monthlyPointPurchases);

      // Tạo đối tượng MonthlyPointUsage
      CompanyDashboardResponse.MonthlyPointUsage monthlyPointUsageItem =
          new CompanyDashboardResponse.MonthlyPointUsage();
      monthlyPointUsageItem.setMonth(monthName);
      monthlyPointUsageItem.setPoints(pointUsageAmount);
      monthlyPointUsageList.add(monthlyPointUsageItem);
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
        .monthScanList(monthRevenueList)
        .top3Employees(top3EmployeesList)
        .numberOfActiveModels(numberOfActiveModels)
        .numberOfInactiveModels(numberOfInactiveModels)
        .top3Guidelines(top3GuidelinesList)
        .monthlyPointPurchases(monthlyPointPurchasesList)
        .numberOfMachines(numberOfMachines)
        .numberOfMachinesType(numberOfMachinesType)
        .monthlyPointUsage(monthlyPointUsageList)
        .build();
  }
}
