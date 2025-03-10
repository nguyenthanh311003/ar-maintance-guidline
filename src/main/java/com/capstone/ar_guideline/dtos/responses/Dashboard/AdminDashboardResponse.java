package com.capstone.ar_guideline.dtos.responses.Dashboard;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
  private Integer numberOfGuidelines;
  private Integer numberOfActiveGuidelines;
  private Integer numberOfInactiveGuidelines;
  private Integer numberOfAccount;
  private Integer numberOfActiveAccount;
  private Integer numberOfInactiveAccount;
  private Integer numberOfModels;
  private Integer numberOfActiveModels;
  private Integer numberOfInactiveModels;
  private Double totalRevenue;
  private List<CompanyRevenue> companyRevenueList;
  private List<SubscriptionRevenue> subscriptionRevenueList;
  private List<MonthRevenue> monthRevenueList;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CompanyRevenue {
    private String name;
    private Double revenue;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SubscriptionRevenue {
    private String name;
    private Double revenue;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MonthRevenue {
    private Long month;
    private Double revenue;
  }
}
