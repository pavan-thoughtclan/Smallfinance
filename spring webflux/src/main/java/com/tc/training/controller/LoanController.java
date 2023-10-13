package com.tc.training.controller;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import com.tc.training.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<LoanOutputDto> addLoan(@RequestBody LoanInputDto loanInputDto){
        return loanService.addLoan(loanInputDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public Mono<LoanOutputDto> getAll(@PathVariable UUID id){
        return loanService.getById(id);
    }

    @GetMapping("/getAllByUser")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public Flux<LoanOutputDto> getAllByUser(@RequestParam Long accNo){
        return loanService.getAllByUser(accNo);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<LoanOutputDto> getAll(){
        return loanService.getAll();
    }

    @PutMapping("/set")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<LoanOutputDto> setLoan(@RequestParam UUID id, @RequestParam String status){
       return loanService.setLoan(id,status);
    }

    @GetMapping("/getByType")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Flux<LoanOutputDto> getByType(@RequestParam Long accNo , @RequestParam String type){
        return loanService.getBytype(accNo,type);
    }

    @GetMapping("/getTotalLoanAmount")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<Double> getTotalLoanAmount(@RequestParam Long accNo){

        return loanService.getTotalLoanAmount(accNo);

    }
    @GetMapping("/getAllPending")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<LoanOutputDto> getAllPending(){
        return loanService.getAllPending();
    }

    @GetMapping("/getAllByNotPending")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<LoanOutputDto> getAllByNotPending(){
        return loanService.getAllByNotPending();
    }
    @GetMapping("/getAllByStatus")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<LoanOutputDto> getAllByStatus(@RequestParam String status){
        return loanService.getAllByStatus(status);
    }
}