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

/**
 * AccountDetailsController to handles all the account related apis
 */
@Controller("/accounts")
public class AccountDetailsController {
    @Inject
    private final AccountDetailsService accountDetailsService;
    @Inject
    private final SecurityService securityService;


    public AccountDetailsController(AccountDetailsService accountDetailsService, SecurityService securityService) {
        this.accountDetailsService = accountDetailsService;

        this.securityService = securityService;
    }

    /**
     * Taking AccountDetailsInput as input to create account and returning account output dto.
     * @param input AccountDetailsInput
     * @return AccountDetailsOutput
     */
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post
    public AccountDetailsOutput create(@Body AccountDetailsInput input) {
        return accountDetailsService.create(input);
    }

    /**
     * Get account details by ID (for ROLE_CUSTOMER only).
     * @param id Account ID
     * @return AccountDetailsOutput
     */

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/{id}")
    public AccountDetailsOutput getById(@PathVariable Long id) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountDetailsService.getById(id);
        throw new RuntimeException("you are not allowed to access this");
    }


    /**
     * Get account details by user ID (for ROLE_CUSTOMER only).
     * @param userId User ID
     * @return AccountDetailsOutput
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/getAccountByUser")
    public AccountDetailsOutput getByUser(@QueryValue UUID userId){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountDetailsService.getByUser(userId);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get account balance by ID (for ROLE_CUSTOMER only).
     * @param id Account ID
     * @return Double representing the account balance
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/getBalance/{id}")
    public Double getBalance(@PathVariable Long id){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountDetailsService.getBalance(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get home page details by ID (for ROLE_CUSTOMER only).
     * @param id Account ID
     * @return HomePageOutput
     */

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/homePage/{id}")
    public HomePageOutput getHomePageDetails(@PathVariable Long id){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return accountDetailsService.getHomePageById(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Verify KYC for an account (for ROLE_CUSTOMER only).
     * @param accNo Account number
     * @return AccountDetailsOutput
     */

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Put("/setKyc")
    public AccountDetailsOutput verifyKyc(@QueryValue Long accNo){

        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return accountDetailsService.verifyKyc(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }
}
