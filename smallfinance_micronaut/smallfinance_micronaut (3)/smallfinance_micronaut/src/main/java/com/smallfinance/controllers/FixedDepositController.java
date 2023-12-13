package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.FixedDepositInput;
import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.services.FixedDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

/**
 * FixedDepositController handles Fixed Deposit related APIs.
 */
@Controller("/fds")
public class FixedDepositController {
    @Inject
    private final FixedDepositService fixedDepositService;
    @Inject
    private final SecurityService securityService;

    public FixedDepositController(FixedDepositService fixedDepositService, SecurityService securityService) {
        this.fixedDepositService = fixedDepositService;
        this.securityService = securityService;
    }

    /**
     * Create a new Fixed Deposit (FD) based on the provided input.
     * @param fixedDepositInputDto FixedDepositInput
     * @return FixedDepositOutput
     */
    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public FixedDepositOutput createFixedDeposit(@Body FixedDepositInput fixedDepositInputDto){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get all Fixed Deposits (FDs) for a specific customer account.
     * @param accNo Account number
     * @return List of FixedDepositOutput
     */
    @Get("/getAllByUser")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public List<FixedDepositOutput> getAllFixedDeposit(@QueryValue Long accNo) {
            return fixedDepositService.allFixedDeposit(accNo);

    }

    /**
     * Get details of a specific Fixed Deposit (FD).
     * @param accNo Account number
     * @return FDDetails
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/getDetails")
    public FDDetails getFDDetails(@QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.getFDDetails(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Break a Fixed Deposit (FD) based on the provided ID.
     * @param id ID of the Fixed Deposit to break
     * @return FixedDepositOutput
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("/break/{id}")
    public FixedDepositOutput breakFixedDeposit(@PathVariable(name = "id") String id) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.breakFixedDeposit(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get details of all Fixed Deposits (FDs).
     * @return List of FixedDepositOutput
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get
    public List<FixedDepositOutput> getAll() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return fixedDepositService.getAll();
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get details of a specific Fixed Deposit (FD) by its ID.
     * @param id ID of the Fixed Deposit
     * @return FixedDepositOutput
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/{id}")
    public FixedDepositOutput getById(@PathVariable(name = "id") UUID id) {
            return fixedDepositService.getById(id);
    }

    /**
     * Get details of all active Fixed Deposits (FDs) for a specific customer account.
     * @param accNo Account number
     * @return List of FixedDepositOutput
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/getAllActive")
    public List<FixedDepositOutput> getAllActive(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return fixedDepositService.getAllActive(accNo);
        throw new RuntimeException("you are not allowed to access this");
    }
}
