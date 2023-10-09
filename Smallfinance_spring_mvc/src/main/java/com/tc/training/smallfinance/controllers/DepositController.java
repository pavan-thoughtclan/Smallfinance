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

@RestController
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    private DepositService  depositService;

    @GetMapping("/getDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FDDetails getDetails(@RequestParam Long accNo){
       return depositService.getDetails(accNo);
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Object> getAccounts(@RequestParam Long accNo){
        return depositService.getAccounts(accNo);
    }
}
