package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.dtos.outputs.HomePageOutputDto;
import com.microFinance1.services.AccountServiceDetails;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS;
import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Slf4j
@Controller("/account")
public class AccountController {
    @Inject
    private AccountServiceDetails accountServiceDetails;

    @Inject
    private SecurityService securityService;

    @Post("/create")
    @Secured(IS_ANONYMOUS)
    public Mono<AccountDetailsOutputDto> createAccount(@Body AccountDetailsInputDto accountDetailsInputDto){
        return accountServiceDetails.createAccount(accountDetailsInputDto);
    }
    @Get("/getBalance/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<Double> getBalance(@PathVariable(name = "id") Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))

//        log.info( "Current user name is {}.",
//                securityService.getAuthentication().get().getAttributes().get("email") );
        return accountServiceDetails.getBalance(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> getAccountDetails(@PathVariable(name = "id") Long accountNumber)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountServiceDetails.getAccount(accountNumber);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getAccountByUser")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> getAccountByUser(@QueryValue UUID userId)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return  accountServiceDetails.getAccountByUser(userId);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/homePage/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<HomePageOutputDto> getHomePageDetails(@PathVariable(name = "id") Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountServiceDetails.getHomePageDetails(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/setKyc/{id}")
    @Secured(IS_AUTHENTICATED)
    public Mono<AccountDetailsOutputDto> verifyKyc(@PathVariable(name = "id") Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return accountServiceDetails.verifyKyc(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
}
