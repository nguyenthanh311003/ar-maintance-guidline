package com.capstone.ar_guideline.mappers;

import com.capstone.ar_guideline.dtos.responses.Wallet.WalletResponse;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.entities.Wallet;

public class WalletMapper {

  public static WalletResponse toResponse(Wallet wallet) {
    if (wallet == null) {
      return null;
    }
    return WalletResponse.builder()
        .id(wallet.getId())
        .userId(wallet.getUser().getId())
        .balance(wallet.getBalance())
        .currency(wallet.getCurrency())
        .build();
  }

  public static Wallet toEntity(WalletResponse walletResponse) {
    if (walletResponse == null) {
      return null;
    }
    Wallet wallet = new Wallet();
    wallet.setId(walletResponse.getId());
    // Assuming you have a method to fetch User by ID
    User user = new User();
    user.setId(walletResponse.getUserId());
    wallet.setUser(user);
    wallet.setBalance(walletResponse.getBalance());
    wallet.setCurrency(walletResponse.getCurrency());
    return wallet;
  }
}
