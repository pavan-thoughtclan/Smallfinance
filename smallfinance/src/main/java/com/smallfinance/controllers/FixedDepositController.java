package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.FixedDepositInput;
import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.services.FixedDepositService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@Controller("/fds")
public class FixedDepositController {
    @Inject
    private final FixedDepositService fixedDepositService;

    public FixedDepositController(FixedDepositService fixedDepositService) {
        this.fixedDepositService = fixedDepositService;
    }

    @Post
    public FixedDepositOutput createFixedDeposit(@Body FixedDepositInput fixedDepositInputDto){
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }
    @Get("/getAllByUser")
    public List<FixedDepositOutput> getAllFixedDeposit(@QueryValue Long accNo) {
        return fixedDepositService.allFixedDeposit(accNo);
    }

    @Get("/getDetails")
    public FDDetails getFDDetails(@QueryValue Long accNo) {
        return fixedDepositService.getFDDetails(accNo);
    }

    @Post("/break/{id}")
    public FixedDepositOutput breakFixedDeposit(@PathVariable(name = "id") String id) {
        return fixedDepositService.breakFixedDeposit(id);
    }

    @Get
    public List<FixedDepositOutput> getAll() {
        return fixedDepositService.getAll();
    }

    @Get("/{id}")
    public FixedDepositOutput getById(@PathVariable(name = "id") UUID id) {
        return fixedDepositService.getById(id);
    }


    @Get("/get_all_active")
    public List<FixedDepositOutput> getAllActive(@QueryValue Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }
}
