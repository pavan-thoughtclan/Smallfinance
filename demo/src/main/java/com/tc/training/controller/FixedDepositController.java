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
    @PostMapping("/createFixedDeposit")
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

    @PostMapping("/break")
    public Mono<FixedDepositOutputDto> breakFixedDeposit(@RequestParam String id){

        return fixedDepositService.breakFixedDeposit(id);
    }

    @GetMapping("/getAll")
    public Flux<FixedDepositOutputDto> getAll(){
       return fixedDepositService.getAll();
    }

    @GetMapping("/getbyId")
    public Mono<FixedDepositOutputDto> getById(@RequestParam UUID id){
        return fixedDepositService.getById(id);
    }

    @GetMapping("/getAllActive")
    public Flux<FixedDepositOutputDto> getAllActive(Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }

}
