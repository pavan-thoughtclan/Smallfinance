package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.services.LoanService;
import io.micronaut.data.annotation.Query;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@Controller("/loans")
public class LoanController {
    @Inject
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @Post("/apply")
    public LoanOutput addLoan(@Body LoanInput loanInputDto){
        return loanService.addLoan(loanInputDto);
    }

    @Get("/{id}")
    public LoanOutput getById(@PathVariable UUID id) {
        return loanService.getById(id);
    }

    @Get("/getAllByUser")
    public List<LoanOutput> getAllByUser(@QueryValue Long accNo) {
        return loanService.getAllByUser(accNo);
    }

    @Get
    public List<LoanOutput> getAll() {
        return loanService.getAll();
    }

    @Put("/set")
    public LoanOutput setLoan(@QueryValue UUID id,@QueryValue String status) {
        return loanService.setLoan(id, status);
    }

    @Get("/getByType")
    public List<LoanOutput> getByType(@QueryValue Long accNo,@QueryValue String type) {
        return loanService.getByType(accNo, type);
    }

    @Get("/getTotalLoanAmount")
    public Double getTotalLoanAmount(@QueryValue Long accNo) {
        return loanService.getTotalLoanAmount(accNo);
    }

    @Get("/getAllPending")
    public List<LoanOutput> getAllPending() {
        return loanService.getAllPending();
    }

    @Get("/getAllByNotPending")
    public List<LoanOutput> getAllByNotPending() {
        return loanService.getAllByNotPending();
    }

    @Get("/getAllByStatus")
    public List<LoanOutput> getAllByStatus(@QueryValue String status) {
        return loanService.getAllByStatus(status);
    }


}
