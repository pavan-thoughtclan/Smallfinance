package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.dtos.outputs.HomePageOutputDto;
import com.microFinance1.services.AccountServiceDetails;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS;
import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/account")
public class AccountController {
    @Inject
    private AccountServiceDetails accountServiceDetails;
    @Post("/create")
    @Secured(IS_ANONYMOUS)
    public Mono<AccountDetailsOutputDto> createAccount(@Body AccountDetailsInputDto accountDetailsInputDto){
        return accountServiceDetails.createAccount(accountDetailsInputDto);
    }
    @Get("/getBalance/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<Double> getBalance(@PathVariable(name = "id") Long accNo){
        return accountServiceDetails.getBalance(accNo);
    }
    @Get("/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> getAccountDetails(@PathVariable(name = "id") Long accountNumber){
       return accountServiceDetails.getAccount(accountNumber);
    }
    @Get("/getAccountByUser")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> getAccountByUser(@QueryValue UUID userId){
        return  accountServiceDetails.getAccountByUser(userId);
    }

    @Get("/homePage/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<HomePageOutputDto> getHomePageDetails(@PathVariable(name = "id") Long accNo){
        return accountServiceDetails.getHomePageDetails(accNo);
    }
    @Get("/setKyc/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> verifyKyc(@PathVariable(name = "id") Long accNo){
        return accountServiceDetails.verifyKyc(accNo);
    }


}
