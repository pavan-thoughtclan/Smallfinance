package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.LoanInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;
import com.tc.training.smallfinance.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CUSTOMER')")
    public LoanOutputDto addLoan(@RequestBody LoanInputDto loanInputDto){
        return loanService.addLoan(loanInputDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public LoanOutputDto getAll(@PathVariable("id") UUID id){
        return loanService.getById(id);
    }

    @GetMapping("/get_all_by_user")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByUser(@RequestParam(required = false) Long accNo){
        return loanService.getAllByUser(accNo);
    }

    @GetMapping("/get_all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<LoanOutputDto> getAll(){
        return loanService.getAll();
    }

    @PutMapping("/set")
    @PreAuthorize("hasRole('MANAGER')")
    public LoanOutputDto setLoan(@RequestParam UUID id,@RequestParam String status){
       return loanService.setLoan(id,status);
    }

    @GetMapping("/get_by_type")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<LoanOutputDto> getByType(@RequestParam Long accNo , @RequestParam String type){
        return loanService.getBytype(accNo,type);
    }

    @GetMapping("/get_total_loan_amount")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Double getTotalLoanAmount(@RequestParam Long accNo){
        return loanService.getTotalLoanAmount(accNo);
    }

    @GetMapping("/get_all_pending")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllPending(){
        return loanService.getAllPending();
    }

    @GetMapping("/get_all_by_not_pending")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByNotPending(){
        return loanService.getAllByNotPending();
    }

    @GetMapping("/get_all_by_status")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LoanOutputDto> getAllByStatus(@RequestParam String status){
        return loanService.getAllByStatus(status);
    }

//    @PutMapping(value = "/upload_suppliments/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public void uploadSuppliment(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2,  @PathVariable UUID id){
//        loanService.uploadSuppliment(file1,file2,id);
//    }

}