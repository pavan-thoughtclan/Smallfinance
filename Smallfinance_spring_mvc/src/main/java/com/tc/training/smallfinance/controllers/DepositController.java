package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.outputs.FDDetails;
import com.tc.training.smallfinance.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DepositController handles deposit-related APIs.
 */

@RestController
@RequestMapping("/deposits")
public class DepositController {
    @Autowired
    private DepositService  depositService;

    /**
     * Get FD (Fixed Deposit) details for a customer's account.
     * @param accNo Account number
     * @return FDDetails
     */

    @GetMapping("/getDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FDDetails getDetails(@RequestParam Long accNo)throws Exception{
       return depositService.getDetails(accNo);
    }

    /**
     * Get a list of accounts related to the customer (for ROLE_CUSTOMER only).
     * @param accNo Account number
     * @return List of accounts
     */

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Object> getAccounts(@RequestParam Long accNo)throws Exception{
        return depositService.getAccounts(accNo);
    }
}
