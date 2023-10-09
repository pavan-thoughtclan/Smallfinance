package com.tc.training.controller;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.HomePageOutputDto;
import com.tc.training.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountDetailsController {

    @Autowired
    private AccountDetailsService accountServiceDetails;

    @GetMapping("/getBalance/{id}")
    public Mono<Double> getBalance(@PathVariable Long id){

        return accountServiceDetails.getBalance(id);
    }

    @PostMapping(value = "/create" ,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<AccountDetailsOutputDto> createAccount(@RequestBody AccountDetailsInputDto accountDetails){
        return accountServiceDetails.createAccount(accountDetails);

    }
    @GetMapping("/getAccountDetails/{id}")
    public Mono<AccountDetailsOutputDto> getAccountDetails(@PathVariable Long id){
        return accountServiceDetails.getAccount(id);

    }

    @GetMapping("/getAccountByUser")
    public Mono<AccountDetailsOutputDto> getAccountByUser(@RequestParam UUID userId){
        return  accountServiceDetails.getAccountByUser(userId);
    }

    @GetMapping("/homePage")
    public Mono<HomePageOutputDto> getHomePageDetails(@RequestParam Long accNo){

        return accountServiceDetails.getHomePageDetails(accNo);

    }
    @GetMapping("/setKyc/{id}")
    public Mono<AccountDetailsOutputDto> verifyKyc(@PathVariable Long id){
        return accountServiceDetails.verifyKyc(id);
    }

}
