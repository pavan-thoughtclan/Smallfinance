package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import com.microFinance1.services.LoanService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/loan")
public class LoanController {
    @Inject
    private LoanService loanService;
    @Post("/apply")
    public Mono<LoanOutputDto> addLoan(@Body LoanInputDto loanInputDto){
        return loanService.addLoan(loanInputDto);
    }
    @Get("/{id}")
    public Mono<LoanOutputDto> getAll(@PathVariable(name = "id") UUID id){
        return loanService.getById(id);
    }

    @Get("/getAllByUser")
    public Flux<LoanOutputDto> getAllByUser(@QueryValue Long accNo){
        return loanService.getAllByUser(accNo);
    }

    @Get()
    public Flux<LoanOutputDto> getAll(){
        return loanService.getAll();
    }

    @Put("/set")
    public Mono<LoanOutputDto> setLoan(@QueryValue UUID id,@QueryValue String status){
        return loanService.setLoan(id,status);
    }
    @Get("/getByType")
    public Flux<LoanOutputDto> getByType(@QueryValue Long accNo , @QueryValue String type){
        return loanService.getByType(accNo,type);
    }

    @Get("/getTotalLoanAmount")
    public Mono<Double> getTotalLoanAmount(@QueryValue Long accNo){
        return loanService.getTotalLoanAmount(accNo);
    }
    @Get("/getAllPending")
    public Flux<LoanOutputDto> getAllPending(){
        return loanService.getAllPending();
    }

    @Get("/getAllByNotPending")
    public Flux<LoanOutputDto> getAllByNotPending(){
        return loanService.getAllByNotPending();
    }
    @Get("/getAllByStatus")
    public Flux<LoanOutputDto> getAllByStatus(@QueryValue String status){
        return loanService.getAllByStatus(status);
    }

}
