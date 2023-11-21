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

/**
 * FixedDepositController handles Fixed Deposit related APIs.
 */

@RestController
@RequestMapping("/fds")
public class FixedDepositController {
    @Autowired
    private FixedDepositService fixedDepositService;

    /**
     * Create a new Fixed Deposit (FD) based on the provided input.
     * @param fixedDepositInputDto FixedDepositInput
     * @return FixedDepositOutput
     */

//    @PostMapping("/create")
    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FixedDepositOutputDto createFixedDeposit(@RequestBody  FixedDepositInputDto fixedDepositInputDto)throws Exception{
        return fixedDepositService.createFixedDeposit(fixedDepositInputDto);
    }

    /**
     * Get all Fixed Deposits (FDs) for a specific customer account.
     * @param accNo Account number
     * @return List of FixedDepositOutput
     */

    @GetMapping("/getAllByUser")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public List<FixedDepositOutputDto> getAllFixedDeposit(@RequestParam Long accNo)throws Exception{
        return fixedDepositService.getAllFixedDeposit(accNo);
    }

    /**
     * Get details of a specific Fixed Deposit (FD).
     * @param accNo Account number
     * @return FDDetails
     */

    @GetMapping("/getDetails")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FDDetails getFDDetails(@RequestParam Long accNo)throws Exception{
        return fixedDepositService.getFDDetails(accNo);
    }

    /**
     * Break a Fixed Deposit (FD) based on the provided ID.
     * @param id ID of the Fixed Deposit to break
     * @return FixedDepositOutput
     */

    @PostMapping("/break/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public FixedDepositOutputDto breakFixedDeposit(@PathVariable(name="id") String id){
        return fixedDepositService.breakFixedDeposit(id);
    }

    /**
     * Get details of all Fixed Deposits (FDs).
     * @return List of FixedDepositOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<FixedDepositOutputDto> getAll()throws Exception{
       return fixedDepositService.getAll();
    }

    /**
     * Get details of a specific Fixed Deposit (FD) by its ID.
     * @param id ID of the Fixed Deposit
     * @return FixedDepositOutput
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public FixedDepositOutputDto getById(@PathVariable("id") UUID id)throws Exception{
        return fixedDepositService.getById(id);
    }

    /**
     * Get details of all active Fixed Deposits (FDs) for a specific customer account.
     * @param accNo Account number
     * @return List of FixedDepositOutput
     */

    @GetMapping("/getAllActive")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public List<FixedDepositOutputDto> getAllActive(Long accNo)throws Exception{
        return fixedDepositService.getAllActive(accNo);
    }

}
