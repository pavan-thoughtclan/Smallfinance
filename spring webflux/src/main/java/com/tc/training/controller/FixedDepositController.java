package com.tc.training.controller;


import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.service.FixedDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fds")
public class FixedDepositController {
    @Autowired
    private FixedDepositService fixedDepositService;
    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<FixedDepositOutputDto> createFixedDeposit(@RequestBody FixedDepositInputDto fixedDepositInputDto){
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }

    @GetMapping("/getAllByUser")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public Flux<FixedDepositOutputDto> getAllFixedDeposit(@RequestParam Long accNo){
        return fixedDepositService.getAllFixedDeposit(accNo);
    }

    @GetMapping("/getDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<FDDetails> getFDDetails(@RequestParam Long accNo){
        return fixedDepositService.getFDDetails(accNo);
    }

    @PostMapping("/break/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<FixedDepositOutputDto> breakFixedDeposit(@PathVariable String id){

        return fixedDepositService.breakFixedDeposit(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<FixedDepositOutputDto> getAll(){
       return fixedDepositService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public Mono<FixedDepositOutputDto> getById(@PathVariable UUID id){
        return fixedDepositService.getById(id);
    }

    @GetMapping("/getAllActive")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public Flux<FixedDepositOutputDto> getAllActive(@RequestParam Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }

}
