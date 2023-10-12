package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.entities.RecurringDeposit;
import com.smallfinance.services.RecurringDepositService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@Controller("/rds")
public class RecurringDepositController {

    @Inject
    private final RecurringDepositService recurringDepositService;

    public RecurringDepositController(RecurringDepositService recurringDepositService) {
        this.recurringDepositService = recurringDepositService;
    }

    @Post
    public RecurringDepositOutput create(@Body RecurringDepositInput recurringDepositInputDto){
        return recurringDepositService.create(recurringDepositInputDto);
    }

    @Get("/{id}")
    public RecurringDepositOutput getById(@PathVariable("id") UUID id){
        return recurringDepositService.getById(id);
    }

    @Get
    public List<RecurringDepositOutput> getAll(){
        return recurringDepositService.getAll();
    }

    @Get("/getTotalMoneyInvested")
    public Double getTotalMoneyInvested(@QueryValue Long accNo){
        return recurringDepositService.getTotalMoneyInvested(accNo);
    }

    @Get("getByStatus")
    public List<RecurringDepositOutput> getByStatus(@QueryValue Long accNo){
        return recurringDepositService.getByStatus(accNo);
    }
}
