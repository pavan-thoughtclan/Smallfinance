package com.tc.training.controller;

import com.tc.training.dtos.inputdto.RecurringDepositInputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import com.tc.training.service.RecurringDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/rd")
public class RecurringDepositController {
    @Autowired
    private RecurringDepositService recurringDepositService;

    @PostMapping("/save")
    public Mono<RecurringDepositOutputDto> saveRd(@RequestBody RecurringDepositInputDto recurringDepositInputDto){

        return recurringDepositService.saveRd(recurringDepositInputDto);

    }

    @GetMapping("/getById")
    public Mono<RecurringDepositOutputDto> getById(@RequestParam UUID id){
        return recurringDepositService.getById(id);
    }

    @GetMapping("/getAll")
    public Flux<RecurringDepositOutputDto> getAll(){
        return recurringDepositService.getAll();
    }

    @GetMapping("/getTotalMoneyInvested")
    public Mono<Double> getTotalMoneyInvested(@RequestParam Long accNo){
        return recurringDepositService.getTotalMoneyInvested(accNo);
    }
    @GetMapping("getByStatus")
    public Flux<RecurringDepositOutputDto> getByStatus(@RequestParam Long accNo){
        return recurringDepositService.getByStatus(accNo);
    }
}
