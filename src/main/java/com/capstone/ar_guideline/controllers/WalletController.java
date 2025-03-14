package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.services.impl.WalletServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WalletController {

  WalletServiceImpl walletService;

  @GetMapping(value = ConstAPI.WalletAPI.WALLET + "/{userId}")
  ApiResponse<WalletResponse> getWalletByUserId(@PathVariable String userId) {
    return ApiResponse.<WalletResponse>builder()
        .result(walletService.findWalletByUserId(userId))
        .build();
  }
}
