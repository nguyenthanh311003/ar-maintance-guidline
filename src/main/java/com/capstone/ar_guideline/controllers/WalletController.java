package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.dtos.responses.Wallet.WalletTransactionResponse;
import com.capstone.ar_guideline.services.impl.WalletServiceImpl;
import com.capstone.ar_guideline.services.impl.WalletTransactionService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WalletController {

  WalletServiceImpl walletService;
  WalletTransactionService walletTransactionService;

  @GetMapping(value = ConstAPI.WalletAPI.WALLET + "/{userId}")
  ApiResponse<WalletResponse> getWalletByUserId(@PathVariable String userId) {
    return ApiResponse.<WalletResponse>builder()
        .result(walletService.findWalletByUserId(userId))
        .build();
  }

  @GetMapping(value = ConstAPI.WalletAPI.WALLET_HISTORY + "/{userId}")
  public ApiResponse<List<WalletTransactionResponse>> getAllByUserId(@PathVariable String userId) {
    List<WalletTransactionResponse> transactions = walletTransactionService.getAllByUserId(userId);
    return ApiResponse.<List<WalletTransactionResponse>>builder()
        .result(transactions)
        .message("Wallet transactions retrieved successfully")
        .build();
  }

  @PostMapping(value = ConstAPI.WalletAPI.WALLET + "/allocation/{companyId}/{limitPoint}")
  public ApiResponse<Void> updatePointForAllEmployeeByCompanyId(
          @PathVariable String companyId, @PathVariable Long limitPoint) {
    walletService.updatePointForAllEmployeeByCompanyId(companyId, limitPoint);
    return ApiResponse.<Void>builder()
            .message("Points updated successfully for all employees")
            .build();
  }

}
