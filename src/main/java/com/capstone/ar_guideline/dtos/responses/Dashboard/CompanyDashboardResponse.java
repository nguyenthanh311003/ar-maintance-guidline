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
public class CompanyDashboardResponse {
  private Integer numberOfGuidelines;
  private Integer numberOfActiveGuidelines;
  private Integer numberOfInactiveGuidelines;
  private Integer numberOfAccount;
  private Integer numberOfActiveAccount;
  private Integer numberOfInactiveAccount;
  private Integer numberOfModels;
  private Integer numberOfActiveModels;
  private Integer numberOfInactiveModels;
  private Integer numberOfMachines;
  private Integer numberOfMachinesType;
  private List<Top3Guidelines> top3Guidelines;
  private List<Top3Guidelines> top3Employees;
  private List<AdminDashboardResponse.MonthRevenue> monthScanList;
  private List<MonthlyPointPurchases> monthlyPointPurchases;
  private List<MonthlyPointUsage> monthlyPointUsage;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Top3Guidelines {
    private String name;
    private Integer scanTimes;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MonthlyPointPurchases {
    private String month;
    private Long amount;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MonthlyPointUsage {
    private String month;
    private Long points;
  }
}
