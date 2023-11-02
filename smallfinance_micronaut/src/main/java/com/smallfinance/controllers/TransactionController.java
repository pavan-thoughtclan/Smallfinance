package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.services.TransactionService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * TransactionController handles operations related to transactions APIs.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/transactions")
public class TransactionController {
    @Inject
    private final TransactionService transactionService;
    @Inject
    private final SecurityService securityService;

    public TransactionController(TransactionService transactionService, SecurityService securityService) {
        this.transactionService = transactionService;
        this.securityService = securityService;
    }

    /**
     * Perform a synchronous transfer transaction and return the transaction details.
     * @param transactionInputDto TransactionInput
     * @param accNo Account number for the transaction
     * @return TransactionOutput
     */
    @Post
    public TransactionOutput transfer(@Body TransactionInput transactionInputDto,@QueryValue Long accNo) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return transactionService.deposit(transactionInputDto, accNo);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get a list of all transactions within a specified date range and of a specific type for an account.
     * @param date1 Start date of the date range
     * @param date2 End date of the date range
     * @param type Type of the transaction
     * @param accNo Account number for the transactions
     * @return List of TransactionOutput
     */
    @Get
    public List<TransactionOutput> allTransaction(
            @QueryValue() LocalDate date1,
            @QueryValue LocalDate date2,
            @QueryValue String type,
            @QueryValue Long accNo) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return transactionService.getAllTransactions(date1, date2, type, accNo);
        throw new RuntimeException("you are not allowed to access this");
    }
}
