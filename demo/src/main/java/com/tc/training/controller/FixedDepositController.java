package com.tc.training.controller;


import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.service.FixedDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fd")
public class FixedDepositController {
    @Autowired
    private FixedDepositService fixedDepositService;
    @PostMapping("/create")
    public Mono<FixedDepositOutputDto> createFixedDeposit(@RequestBody FixedDepositInputDto fixedDepositInputDto){
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }

    @GetMapping("/getAllByUser")
    public Flux<FixedDepositOutputDto> getAllFixedDeposit(@RequestParam Long accNo){
        return fixedDepositService.getAllFixedDeposit(accNo);
    }

    @GetMapping("/getDetails")
    public Mono<FDDetails> getFDDetails(@RequestParam Long accNo){
        return fixedDepositService.getFDDetails(accNo);
    }

    @PostMapping("/break/{id}")
    public Mono<FixedDepositOutputDto> breakFixedDeposit(@PathVariable String id){

        return fixedDepositService.breakFixedDeposit(id);
    }

    @GetMapping("")
    public Flux<FixedDepositOutputDto> getAll(){
       return fixedDepositService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<FixedDepositOutputDto> getById(@PathVariable UUID id){
        return fixedDepositService.getById(id);
    }

    @GetMapping("/getAllActive")
    public Flux<FixedDepositOutputDto> getAllActive(@RequestParam Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }

}
