package com.smallfinance.controllers;

import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.services.DepositService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/deposits")
public class DepositController {
    @Inject
    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @Get("/getDetails")
    public FDDetails getDetails(@QueryValue Long accNo){
        return depositService.getDetails(accNo);
    }

    @Get("/getByAccount")
    public List<Object> getAccounts(@QueryValue Long accNo){
        return depositService.getAccounts(accNo);
    }
}
