package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.AccountDetailsOutput;
import com.smallfinance.dtos.outputs.HomePageOutput;
import com.smallfinance.services.AccountDetailsService;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import io.micronaut.http.annotation.*;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import javax.transaction.Transactional;
import java.util.UUID;

@Controller("/accounts")
public class AccountDetailsController {
    @Inject
    private final AccountDetailsService accountDetailsService;
    private final SecurityService securityService;


    public AccountDetailsController(AccountDetailsService accountDetailsService, SecurityService securityService) {
        this.accountDetailsService = accountDetailsService;

        this.securityService = securityService;
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/create")
    @Transactional
    public AccountDetailsOutput create(@Body AccountDetailsInput input) {
        return accountDetailsService.create(input);
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/{id}")
    public AccountDetailsOutput getById(@PathVariable Long id) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountDetailsService.getById(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    @Get("/getAccountByUser")
    public AccountDetailsOutput getByUser(@QueryValue UUID userId){
        return accountDetailsService.getByUser(userId);
    }

    @Get("/getBalance/{id}")
    public Double getBalance(@PathVariable Long id){
        return accountDetailsService.getBalance(id);
    }

    @Get("/homePage/{id}")
    public HomePageOutput getHomePageDetails(@PathVariable Long id){
        return accountDetailsService.getHomePageById(id);
    }

    @Get("/setKyc")
    public AccountDetailsOutput verifyKyc(@QueryValue Long accNo){
        return accountDetailsService.verifyKyc(accNo);
    }
}
