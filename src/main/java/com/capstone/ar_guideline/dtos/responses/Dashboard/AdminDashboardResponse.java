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
  private Integer numberOfDoneRequests;
  private Integer numberOfProcessingRequests;
  private Integer numberOfPendingRequests;
  private Long totalRevenue;
  private List<CompanyRevenue> top3Company;
  private List<MonthRevenue> monthRevenueList;
  private List<PointOptionRevenue> pointOptionRevenueList;
  private List<CompanyDashboardResponse.Top3Guidelines> top3Guidelines;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CompanyRevenue {
    private String name;
    private Long revenue;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PointOptionRevenue {
    private String name;
    private Long revenue;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MonthRevenue {
    private Long month;
    private Long revenue;
  }
}
