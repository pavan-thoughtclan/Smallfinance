package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.services.LoanService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

/**
 * LoanController handles loan-related APIs.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/loans")
public class LoanController {
    @Inject
    private final LoanService loanService;
    @Inject
    private final SecurityService securityService;

    public LoanController(LoanService loanService, SecurityService securityService) {
        this.loanService = loanService;
        this.securityService = securityService;
    }

    /**
     * Apply for a new loan based on the provided loanInput input dto.
     * @param loanInputDto LoanInput
     * @return LoanOutput
     */
    @Post
    public LoanOutput addLoan(@Body LoanInput loanInputDto){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.addLoan(loanInputDto);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get loan details by its ID.
     * @param id Loan ID
     * @return LoanOutput
     */
    @Get("/{id}")
    public LoanOutput getById(@PathVariable UUID id) {
         return loanService.getById(id);
    }
    /**
     * Get all loans for a specific user account.
     * @param accNo Account number
     * @return List of LoanOutput
     */

    @Get("/getAllByUser")
    public List<LoanOutput> getAllByUser(@QueryValue Long accNo) {
            return loanService.getAllByUser(accNo);
    }

    /**
     * Get all loans.
     * @return List of LoanOutput
     */
    @Get
    public List<LoanOutput> getAll() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return loanService.getAll();
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Set the status of a loan by its ID.
     * @param id Loan ID
     * @param status New status for the loan
     * @return LoanOutput
     */

    @Put("/set")
    public LoanOutput setLoan(@QueryValue UUID id,@QueryValue String status) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return loanService.setLoan(id, status);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get loans by type for a specific user account.
     * @param accNo Account number
     * @param type Loan type
     * @return List of LoanOutput
     */
    @Get("/getByType")
    public List<LoanOutput> getByType(@QueryValue Long accNo,@QueryValue String type) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getByType(accNo, type);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get the total loan amount for a specific user account.
     * @param accNo Account number
     * @return Total loan amount
     */
    @Get("/getTotalLoanAmount")
    public Double getTotalLoanAmount(@QueryValue Long accNo) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getTotalLoanAmount(accNo);
        throw new RuntimeException("you are not allowed to access this");

    }


    /**
     * Get all loans with pending status.
     * @return List of LoanOutput
     */
    @Get("/getAllPending")
    public List<LoanOutput> getAllPending() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return loanService.getAllPending();
        throw new RuntimeException("you are not allowed to access this");

    }


    /**
     * Get all loans with non-pending status for a specific user account.
     * @return List of LoanOutput
     */
    @Get("/getAllByNotPending")
    public List<LoanOutput> getAllByNotPending() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return loanService.getAllByNotPending();
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get all loans by a specific status.
     * @param status Loan status
     * @return List of LoanOutput
     */
    @Get("/getAllByStatus")
    public List<LoanOutput> getAllByStatus(@QueryValue String status) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return loanService.getAllByStatus(status);
        throw new RuntimeException("you are not allowed to access this");
    }


}
