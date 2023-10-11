package com.tc.training.service;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.HomePageOutputDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface AccountDetailsService {


    Mono<Double> getBalance(Long accNo);

    Mono<AccountDetailsOutputDto> createAccount(AccountDetailsInputDto accountDetails);

    Mono<AccountDetailsOutputDto> getAccount(Long accountNumber);

    Mono<AccountDetailsOutputDto> getAccountByUser(UUID userId);

    Mono<HomePageOutputDto> getHomePageDetails(Long accNo);

    Mono<AccountDetailsOutputDto> verifyKyc(Long accNo);
}
