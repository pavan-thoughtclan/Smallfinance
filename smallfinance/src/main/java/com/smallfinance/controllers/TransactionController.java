package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.services.TransactionService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@Controller("/transactions")
public class TransactionController {
    @Inject
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Post("/transfer")
    public TransactionOutput transferSync(@Body TransactionInput transactionInputDto,@QueryValue Long accNo) {
        return transactionService.deposit(transactionInputDto, accNo);
    }

    @Get("/allTransaction")
    public List<TransactionOutput> allTransaction(
            @QueryValue LocalDate date1,
            @QueryValue LocalDate date2,
            @QueryValue String type,
            @QueryValue Long accNo) {
        return transactionService.getAllTransactions(date1, date2, type, accNo);
    }
}
