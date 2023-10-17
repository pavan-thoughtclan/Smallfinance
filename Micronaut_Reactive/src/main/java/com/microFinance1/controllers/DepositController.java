package com.microFinance1.controllers;

import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.services.DepositService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.utils.SecurityService;
import io.micronaut.security.authentication.AuthenticationException;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;


@Controller("/deposits")
public class DepositController {
    @Inject
    private DepositService depositService;
    @Inject
    private SecurityService securityService;

    @Get("/getDetails")
    @Secured(IS_AUTHENTICATED)
    public Mono<FDDetails> getDetails(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return depositService.getDetails(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getAccounts")
    @Secured(IS_AUTHENTICATED)
    public Flux<Object> getAccounts(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
        return depositService.getAccounts(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
}
