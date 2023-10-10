package com.microFinance1.controllers;

import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.services.DepositService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;


@Controller("/deposit")
public class DepositController {
    @Inject
    private DepositService depositService;

    @Get("/getDetails")
    @Secured(IS_AUTHENTICATED)
    public Mono<FDDetails> getDetails(@QueryValue Long accNo){
        return depositService.getDetails(accNo);
    }
    @Get("/getAccounts")
    @Secured(IS_AUTHENTICATED)
    public Flux<Object> getAccounts(@QueryValue Long accNo){
        return depositService.getAccounts(accNo);
    }
}
