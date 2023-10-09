package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.service.RecurringDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rd")
public class RecurringDepositController {
    @Autowired
    private RecurringDepositService recurringDepositService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('CUSTOMER')")
    public RecurringDepositOutputDto saveRd(@RequestBody RecurringDepositInputDto recurringDepositInputDto){

        return recurringDepositService.saveRd(recurringDepositInputDto);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public RecurringDepositOutputDto getById(@PathVariable("id") UUID id){
        return recurringDepositService.getById(id);
    }

    @GetMapping("/get_all")
    @PreAuthorize("hasRole('MANAGER')")
    public List<RecurringDepositOutputDto> getAll(){
        return recurringDepositService.getAll();
    }


    @GetMapping("/get_total_money_invested")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getTotalMoneyInvested(@RequestParam Long accNo){
        return recurringDepositService.getTotalMoneyInvested(accNo);
    }
    @GetMapping("get_by_status")
    @PreAuthorize("hasRole('MANAGER')")
    public List<RecurringDepositOutputDto> getByStatus(@RequestParam Long accNo){
        return recurringDepositService.getByStatus(accNo);
    }
}
