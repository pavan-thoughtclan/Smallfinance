package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import com.microFinance1.exceptions.CustomException;
import com.microFinance1.services.LoanService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/loans")
public class LoanController {
    @Inject
    private LoanService loanService;
    @Inject
    private SecurityService securityService;
    @Post()
    public Mono<LoanOutputDto> addLoan(@Body LoanInputDto loanInputDto){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.addLoan(loanInputDto);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/{id}")
    public Mono<LoanOutputDto> getAll(@PathVariable(name = "id") UUID id){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getById(id);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getAllByUser")
    public Flux<LoanOutputDto> getAllByUser(@QueryValue Long accNo){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getAllByUser(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get()
    public Flux<LoanOutputDto> getAll(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getAll();
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Put("/set")
    public Mono<LoanOutputDto> setLoan(@QueryValue UUID id,@QueryValue String status){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return loanService.setLoan(id,status);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getByType")
    public Flux<LoanOutputDto> getByType(@QueryValue Long accNo , @QueryValue String type)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getByType(accNo,type);
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getTotalLoanAmount")
    public Mono<Double> getTotalLoanAmount(@QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return loanService.getTotalLoanAmount(accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getAllPending")
    public Flux<LoanOutputDto> getAllPending(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return loanService.getAllPending();
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/getAllByNotPending")
    public Flux<LoanOutputDto> getAllByNotPending(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return loanService.getAllByNotPending();
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get("/getAllByStatus")
    public Flux<LoanOutputDto> getAllByStatus(@QueryValue String status){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return loanService.getAllByStatus(status);
        throw new AuthenticationException("you are not allowed to access this");
    }

}
