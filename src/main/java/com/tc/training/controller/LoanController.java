package com.tc.training.controller;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import com.tc.training.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public Mono<LoanOutputDto> addLoan(@RequestBody LoanInputDto loanInputDto){
        return loanService.addLoan(loanInputDto);
    }

    @GetMapping("/getById")
    public Mono<LoanOutputDto> getAll(@RequestParam UUID id){
        return loanService.getById(id);
    }

    @GetMapping("/getAllByUser")
    public Flux<LoanOutputDto> getAllByUser(@RequestParam Long accNo){
        return loanService.getAllByUser(accNo);
    }

    @GetMapping("/getAll")
    public Flux<LoanOutputDto> getAll(){
        return loanService.getAll();
    }

    @PutMapping("/set")
    public Mono<LoanOutputDto> setLoan(@RequestParam UUID id, @RequestParam String status){
       return loanService.setLoan(id,status);
    }

    @GetMapping("/getByType")
    public Flux<LoanOutputDto> getByType(@RequestParam Long accNo , @RequestParam String type){
        return loanService.getBytype(accNo,type);
    }

    @GetMapping("/getTotalLoanAmount")
    public Mono<Double> getTotalLoanAmount(@RequestParam Long accNo){

        return loanService.getTotalLoanAmount(accNo);

    }
    @GetMapping("/getAllPending")
    public Flux<LoanOutputDto> getAllPending(){
        return loanService.getAllPending();
    }

    @GetMapping("/getAllByNotPending")
    public Flux<LoanOutputDto> getAllByNotPending(){
        return loanService.getAllByNotPending();
    }
    @GetMapping("/getAllByStatus")
    public Flux<LoanOutputDto> getAllByStatus(@RequestParam String status){
        return loanService.getAllByStatus(status);
    }
}