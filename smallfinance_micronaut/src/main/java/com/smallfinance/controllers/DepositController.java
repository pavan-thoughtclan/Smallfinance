package com.smallfinance.controllers;

import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.services.DepositService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;

/**
 * DepositController handles deposit-related APIs.
 */
@Controller("/deposits")
public class DepositController {
    @Inject
    private final DepositService depositService;
    @Inject
    private final SecurityService securityService;

    public DepositController(DepositService depositService, SecurityService securityService) {
        this.depositService = depositService;
        this.securityService = securityService;
    }

    /**
     * Get FD (Fixed Deposit) details for a customer's account.
     * @param accNo Account number
     * @return FDDetails
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/getDetails")
    public FDDetails getDetails(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return depositService.getDetails(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get a list of accounts related to the customer (for ROLE_CUSTOMER only).
     * @param accNo Account number
     * @return List of accounts
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/get")
    public List<Object> getAccounts(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return depositService.getAccounts(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }
}
