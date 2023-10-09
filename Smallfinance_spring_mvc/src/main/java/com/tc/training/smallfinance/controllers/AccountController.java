package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.outputs.AccountDetailsOutputDto;
import com.tc.training.smallfinance.dtos.outputs.HomePageOutputDto;
import com.tc.training.smallfinance.service.AccountServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceDetails accountServiceDetails;

    @PostMapping(value = "/create" ,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AccountDetailsOutputDto> createAccount(@RequestBody AccountDetailsInputDto accountDetails){
        AccountDetailsOutputDto createdAccount=accountServiceDetails.createAccount(accountDetails);
        return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountDetails(@PathVariable(name="id") Long accountNumber){
        AccountDetailsOutputDto getAccount=accountServiceDetails.getAccount(accountNumber);
        return getAccount;
    }
    @GetMapping("/getBalance/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getBalance(@PathVariable(name="id") Long accNo){
        return accountServiceDetails.getBalance(accNo);
    }

    @GetMapping("/getAccountByUser")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountByUser(@RequestParam UUID userId){
       return  accountServiceDetails.getAccountByUser(userId);
    }

    @GetMapping("/homePage/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public HomePageOutputDto getHomePageDetails(@PathVariable(name="id") Long accNo){
        return accountServiceDetails.getHomePageDetails(accNo);
    }

    @GetMapping("/setKyc/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDetailsOutputDto verifyKyc(@PathVariable(name="id") Long accNo){
        return accountServiceDetails.verifyKyc(accNo);
    }
}
