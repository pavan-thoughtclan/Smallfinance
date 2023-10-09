package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.outputs.AccountDetailsOutputDto;
import com.tc.training.smallfinance.dtos.outputs.HomePageOutputDto;

import java.util.UUID;

public interface AccountServiceDetails {
    public AccountDetailsOutputDto createAccount(AccountDetailsInputDto accountDetailsInputDto);

    public AccountDetailsOutputDto getAccount(Long accountNumber);

    Double getBalance(Long accNo);

    AccountDetailsOutputDto getAccountByUser(UUID userId);

    HomePageOutputDto getHomePageDetails(Long accNo);

    AccountDetailsOutputDto verifyKyc(Long accNo);
}
