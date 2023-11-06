package com.microFinance1.services;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.dtos.outputs.HomePageOutputDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountServiceDetails {
    public Mono<AccountDetailsOutputDto> createAccount(AccountDetailsInputDto accountDetailsInputDto);

    public Mono<AccountDetailsOutputDto> getAccount(Long accountNumber);

    Mono<Double> getBalance(Long accNo);

    Mono<AccountDetailsOutputDto> getAccountByUser(UUID userId);

    Mono<HomePageOutputDto> getHomePageDetails(Long accNo);

    Mono<AccountDetailsOutputDto> verifyKyc(Long accNo);
}
