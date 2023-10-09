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
    @GetMapping("getAccountDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountDetails(@RequestParam Long accountNumber){
        AccountDetailsOutputDto getAccount=accountServiceDetails.getAccount(accountNumber);
        return getAccount;
    }
    @GetMapping("/getBalance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getBalance(@RequestParam Long accNo){
        return accountServiceDetails.getBalance(accNo);
    }

    @GetMapping("/getAccountByUser")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountByUser(@RequestParam UUID userId){
       return  accountServiceDetails.getAccountByUser(userId);
    }

    @GetMapping("/homePage")
    @PreAuthorize("hasRole('MANAGER')")
    public HomePageOutputDto getHomePageDetails(@RequestParam Long accNo){
        return accountServiceDetails.getHomePageDetails(accNo);
    }
    @GetMapping("/setKyc")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDetailsOutputDto verifyKyc(@RequestParam Long accNo){
        return accountServiceDetails.verifyKyc(accNo);
    }
}
