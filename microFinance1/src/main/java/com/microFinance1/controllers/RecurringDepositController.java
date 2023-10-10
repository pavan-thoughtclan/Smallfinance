package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.services.RecurringDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/rd")
public class RecurringDepositController {
    @Inject
    private RecurringDepositService recurringDepositService;

    @Post("/save")
    public Mono<RecurringDepositOutputDto> saveRd(@Body RecurringDepositInputDto recurringDepositInputDto){
        return recurringDepositService.saveRd(recurringDepositInputDto);
    }

    @Get("/{id}")
    public Mono<RecurringDepositOutputDto> getById(@PathVariable(name = "id") UUID id){
        return recurringDepositService.getById(id);
    }

    @Get()
    public Flux<RecurringDepositOutputDto> getAll(){
        return recurringDepositService.getAll();
    }

    @Get("/getTotalMoneyInvested")
    public Mono<Double> getTotalMoneyInvested(@QueryValue Long accNo){
        return recurringDepositService.getTotalMoneyInvested(accNo);
    }
    @Get("getByStatus")
    public Flux<RecurringDepositOutputDto> getByStatus(@QueryValue Long accNo){
        return recurringDepositService.getByStatus(accNo);
    }
}
