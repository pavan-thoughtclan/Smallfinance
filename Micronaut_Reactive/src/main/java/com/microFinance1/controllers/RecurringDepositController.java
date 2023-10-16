package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.services.RecurringDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/rds")
public class RecurringDepositController {
    @Inject
    private RecurringDepositService recurringDepositService;
    @Inject
    private SecurityService securityService;

    @Post()
    public Mono<RecurringDepositOutputDto> saveRd(@Body RecurringDepositInputDto recurringDepositInputDto)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.saveRd(recurringDepositInputDto);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/{id}")
    public Mono<RecurringDepositOutputDto> getById(@PathVariable(name = "id") UUID id)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getById(id);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get()
    public Flux<RecurringDepositOutputDto> getAll()
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getAll();
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getTotalMoneyInvested")
    public Mono<Double> getTotalMoneyInvested(@QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getTotalMoneyInvested(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("getByStatus")
    public Flux<RecurringDepositOutputDto> getByStatus(@QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return recurringDepositService.getByStatus(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
}
