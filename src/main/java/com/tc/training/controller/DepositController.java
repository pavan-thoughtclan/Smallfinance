package com.tc.training.controller;

import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @GetMapping("/getDetails")
    public Mono<FDDetails> getDetails(@RequestParam Long accNo){

       return depositService.getDetails(accNo);

    }
    @GetMapping("/get")
    public Flux<Object> getAccounts(@RequestParam Long accNo){

        return depositService.getAccounts(accNo);

    }
}
