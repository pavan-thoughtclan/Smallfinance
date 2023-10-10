package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.services.FixedDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;
@Secured(IS_AUTHENTICATED)
@Controller("/fd")
public class FixedDepositController {
    @Inject
    private FixedDepositService fixedDepositService;

    @Post("/create")
    public Mono<FixedDepositOutputDto> createFixedDeposit(@Body FixedDepositInputDto fixedDepositInputDto){
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }

    @Get("/getAllByUser")
    public Flux<FixedDepositOutputDto> getAllFixedDeposit(@QueryValue Long accNo){
        return fixedDepositService.allFixedDeposit(accNo);
    }
    @Get("/getDetails")
    public Mono<FDDetails> getFDDetails(@QueryValue Long accNo){
        return fixedDepositService.getFDDetails(accNo);
    }
    @Post("/break/{id}")
    public Mono<FixedDepositOutputDto> breakFixedDeposit(@PathVariable(name="id") String id){
        return fixedDepositService.breakFixedDeposit(id);
    }
    @Get()
    public Flux<FixedDepositOutputDto> getAll(){
        return fixedDepositService.getAll();
    }

    @Get("/{id}")
    public Mono<FixedDepositOutputDto> getById(@PathVariable(name = "id") UUID id){
        return fixedDepositService.getById(id);
    }

    @Get("/getAllActive")
    public Flux<FixedDepositOutputDto> getAllActive(Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }


}
