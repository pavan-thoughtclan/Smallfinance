package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.services.RecurringDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

/**
 * RecurringDepositController handles recurring deposit-related APIs.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/rds")
public class RecurringDepositController {

    @Inject
    private final RecurringDepositService recurringDepositService;
    @Inject
    private final SecurityService securityService;

    public RecurringDepositController(RecurringDepositService recurringDepositService, SecurityService securityService) {
        this.recurringDepositService = recurringDepositService;
        this.securityService = securityService;
    }

    /**
     * Create a new recurring deposit based on the provided input.
     * @param recurringDepositInputDto RecurringDepositInput
     * @return RecurringDepositOutput
     */
    @Post
    public RecurringDepositOutput create(@Body RecurringDepositInput recurringDepositInputDto){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.create(recurringDepositInputDto);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get recurring deposit details by its ID.
     * @param id Recurring deposit ID
     * @return RecurringDepositOutput
     */
    @Get("/{id}")
    public RecurringDepositOutput getById(@PathVariable UUID id){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER") || securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return recurringDepositService.getById(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get all recurring deposits.
     * @return List of RecurringDepositOutput
     */
    @Get
    public List<RecurringDepositOutput> getAll(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return recurringDepositService.getAll();
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get the total amount of money invested in recurring deposits for a specific user account.
     * @param accNo Account number
     * @return Total money invested
     */
    @Get("/getTotalMoneyInvested")
    public Double getTotalMoneyInvested(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getTotalMoneyInvested(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get recurring deposits by status for a specific user account.
     * @param accNo Account number
     * @return List of RecurringDepositOutput
     */
    @Get("getByStatus")
    public List<RecurringDepositOutput> getByStatus(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getByStatus(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }
}
