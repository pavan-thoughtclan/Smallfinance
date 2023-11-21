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

/**
 * AccountDetailsController to handles all the account related apis
 */

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountServiceDetails accountServiceDetails;

    /**
     * Taking AccountDetailsInput as input to create account and returning account output dto.
     * @param accountDetails AccountDetailsInput
     * @return AccountDetailsOutput
     */

//    @PostMapping(value = "/create")
    @PostMapping("")
    public ResponseEntity<AccountDetailsOutputDto> createAccount(@RequestBody AccountDetailsInputDto accountDetails)
            throws Exception{
        AccountDetailsOutputDto createdAccount=accountServiceDetails.createAccount(accountDetails);
        return ResponseEntity.ok(createdAccount);
    }

    /**
     * Get account details by ID (for ROLE_CUSTOMER only).
     * @param accountNumber Account ID
     * @return AccountDetailsOutput
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountDetails(@PathVariable(name="id") Long accountNumber)
            throws Exception{
        AccountDetailsOutputDto getAccount=accountServiceDetails.getAccount(accountNumber);
        return getAccount;
    }

    /**
     * Get account balance by ID (for ROLE_CUSTOMER only).
     * @param accNo Account ID
     * @return Double representing the account balance
     */

    @GetMapping("/getBalance/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public Double getBalance(@PathVariable(name="id") Long accNo)
            throws Exception{
        return accountServiceDetails.getBalance(accNo);
    }

    /**
     * Get account details by user ID (for ROLE_CUSTOMER only).
     * @param userId User ID
     * @return AccountDetailsOutput
     */

    @GetMapping("/getAccountByUser")
    @PreAuthorize("hasRole('CUSTOMER')")
    public AccountDetailsOutputDto getAccountByUser(@RequestParam UUID userId)throws Exception{
       return  accountServiceDetails.getAccountByUser(userId);
    }

    /**
     * Get home page details by ID (for ROLE_CUSTOMER only).
     * @param accNo Account ID
     * @return HomePageOutput
     */

    @GetMapping("/homePage/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public HomePageOutputDto getHomePageDetails(@PathVariable(name="id") Long accNo)throws Exception{
        return accountServiceDetails.getHomePageDetails(accNo);
    }

    /**
     * Verify KYC for an account (for ROLE_CUSTOMER only).
     * @param accNo Account number
     * @return AccountDetailsOutput
     */

    @PutMapping("/setKyc/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDetailsOutputDto verifyKyc(@PathVariable(name="id") Long accNo)throws Exception{
        return accountServiceDetails.verifyKyc(accNo);
    }
}
