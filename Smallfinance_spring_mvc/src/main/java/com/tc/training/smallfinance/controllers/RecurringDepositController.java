package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.service.RecurringDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RecurringDepositController handles recurring deposit-related APIs.
 */

@RestController
@RequestMapping("/rds")
public class RecurringDepositController {
    @Autowired
    private RecurringDepositService recurringDepositService;

    /**
     * Create a new recurring deposit based on the provided input.
     * @param recurringDepositInputDto RecurringDepositInput
     * @return RecurringDepositOutput
     */

//    @PostMapping("/save")
    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public RecurringDepositOutputDto saveRd(@RequestBody RecurringDepositInputDto recurringDepositInputDto){
        return recurringDepositService.saveRd(recurringDepositInputDto);
    }

    /**
     * Get recurring deposit details by its ID.
     * @param id Recurring deposit ID
     * @return RecurringDepositOutput
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public RecurringDepositOutputDto getById(@PathVariable("id") UUID id){
        return recurringDepositService.getById(id);
    }

    /**
     * Get all recurring deposits.
     * @return List of RecurringDepositOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<RecurringDepositOutputDto> getAll(){
        return recurringDepositService.getAll();
    }

    /**
     * Get the total amount of money invested in recurring deposits for a specific user account.
     * @param accNo Account number
     * @return Total money invested
     */

    @GetMapping("/getTotalMoneyInvested")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getTotalMoneyInvested(@RequestParam Long accNo){
        return recurringDepositService.getTotalMoneyInvested(accNo);
    }

    /**
     * Get recurring deposits by status for a specific user account.
     * @param accNo Account number
     * @return List of RecurringDepositOutput
     */

    @GetMapping("getByStatus")
    @PreAuthorize("hasRole('MANAGER')")
    public List<RecurringDepositOutputDto> getByStatus(@RequestParam Long accNo){
        return recurringDepositService.getByStatus(accNo);
    }
}
