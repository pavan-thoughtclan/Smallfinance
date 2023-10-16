package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.LoanInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;
import com.tc.training.smallfinance.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * LoanController handles loan-related APIs.
 */

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    /**
     * Apply for a new loan based on the provided loanInput input dto.
     * @param loanInputDto LoanInput
     * @return LoanOutput
     */

//    @PostMapping("/apply")
    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public LoanOutputDto addLoan(@RequestBody LoanInputDto loanInputDto){
        return loanService.addLoan(loanInputDto);
    }

    /**
     * Get loan details by its ID.
     * @param id Loan ID
     * @return LoanOutput
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public LoanOutputDto getAll(@PathVariable("id") UUID id){
        return loanService.getById(id);
    }

    /**
     * Get all loans for a specific user account.
     * @param accNo Account number
     * @return List of LoanOutput
     */

    @GetMapping("/getAllByUser")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByUser(@RequestParam(required = false) Long accNo){
        return loanService.getAllByUser(accNo);
    }

    /**
     * Get all loans.
     * @return List of LoanOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<LoanOutputDto> getAll(){
        return loanService.getAll();
    }

    /**
     * Set the status of a loan by its ID.
     * @param id Loan ID
     * @param status New status for the loan
     * @return LoanOutput
     */

    @PutMapping("/set")
    @PreAuthorize("hasRole('MANAGER')")
    public LoanOutputDto setLoan(@RequestParam UUID id,@RequestParam String status){
       return loanService.setLoan(id,status);
    }

    /**
     * Get loans by type for a specific user account.
     * @param accNo Account number
     * @param type Loan type
     * @return List of LoanOutput
     */

    @GetMapping("/getByType")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<LoanOutputDto> getByType(@RequestParam Long accNo , @RequestParam String type){
        return loanService.getBytype(accNo,type);
    }

    /**
     * Get the total loan amount for a specific user account.
     * @param accNo Account number
     * @return Total loan amount
     */

    @GetMapping("/getTotalLoanAmount")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getTotalLoanAmount(@RequestParam Long accNo){
        return loanService.getTotalLoanAmount(accNo);
    }

    /**
     * Get all loans with pending status.
     * @return List of LoanOutput
     */

    @GetMapping("/getAllPending")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllPending(){
        return loanService.getAllPending();
    }

    /**
     * Get all loans with non-pending status for a specific user account.
     * @return List of LoanOutput
     */

    @GetMapping("/getAllByNotPending")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByNotPending(){
        return loanService.getAllByNotPending();
    }

    /**
     * Get all loans by a specific status.
     * @param status Loan status
     * @return List of LoanOutput
     */

    @GetMapping("/getAllByStatus")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByStatus(@RequestParam String status){
        return loanService.getAllByStatus(status);
    }
}