package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.FixedDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.FDDetails;
import com.tc.training.smallfinance.dtos.outputs.FixedDepositOutputDto;
import com.tc.training.smallfinance.service.FixedDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fd")
public class FixedDepositController {
    @Autowired
    private FixedDepositService fixedDepositService;
    @PostMapping("/createFixedDeposit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FixedDepositOutputDto createFixedDeposit(@RequestBody  FixedDepositInputDto fixedDepositInputDto){
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('MANAGER')")
    public List<FixedDepositOutputDto> getAllFixedDeposit(@RequestParam Long accNo){
        return fixedDepositService.getAllFixedDeposit(accNo);
    }

    @GetMapping("/getDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FDDetails getFDDetails(@RequestParam Long accNo){
        return fixedDepositService.getFDDetails(accNo);
    }

    @PostMapping("/break")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FixedDepositOutputDto breakFixedDeposit(@RequestParam String id){
        return fixedDepositService.breakFixedDeposit(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MANAGER')")
    public List<FixedDepositOutputDto> getAll(){
       return fixedDepositService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FixedDepositOutputDto getById(@PathVariable("id") UUID id){
        return fixedDepositService.getById(id);
    }

    @GetMapping("/getAllActive")
    @PreAuthorize("hasRole('MANAGER')")
    public List<FixedDepositOutputDto> getAllActive(Long accNo){
        return fixedDepositService.getAllActive(accNo);
    }

}
