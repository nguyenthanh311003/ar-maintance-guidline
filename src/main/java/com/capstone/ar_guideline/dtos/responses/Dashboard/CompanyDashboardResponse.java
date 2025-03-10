package com.capstone.ar_guideline.dtos.responses.Dashboard;

import com.capstone.ar_guideline.dtos.responses.CompanySubscription.CompanySubscriptionResponse;
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
  private CompanySubscriptionResponse companySubscriptionResponse;
  private List<Top3Guidelines> top3Guidelines;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Top3Guidelines {
    private String name;
    private Integer scanTimes;
  }
}
