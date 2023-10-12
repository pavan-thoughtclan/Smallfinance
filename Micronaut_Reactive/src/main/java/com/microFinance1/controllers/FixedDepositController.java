package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.services.FixedDepositService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.utils.SecurityService;
import io.micronaut.security.authentication.AuthenticationException;
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
    @Inject
    private SecurityService securityService;

    @Post("/create")
    public Mono<FixedDepositOutputDto> createFixedDeposit(@Body FixedDepositInputDto fixedDepositInputDto)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getAllByUser")
    public Flux<FixedDepositOutputDto> getAllFixedDeposit(@QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.allFixedDeposit(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getDetails")
    public Mono<FDDetails> getFDDetails(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.getFDDetails(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Post("/break/{id}")
    public Mono<FixedDepositOutputDto> breakFixedDeposit(@PathVariable(name="id") String id)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.breakFixedDeposit(id);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get()
    public Flux<FixedDepositOutputDto> getAll(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.getAll();
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/{id}")
    public Mono<FixedDepositOutputDto> getById(@PathVariable(name = "id") UUID id)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.getById(id);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getAllActive")
    public Flux<FixedDepositOutputDto> getAllActive(Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return fixedDepositService.getAllActive(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }


}
