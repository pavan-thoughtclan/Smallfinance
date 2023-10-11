package com.tc.training.controller;

import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Mono<TransactionOutputDto> transfer(@RequestBody TransactionInputDto transactionInputDto, @RequestParam Long accNo){

        return transactionService.deposit(transactionInputDto,accNo);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Flux<TransactionOutputDto> transactionHistory(@RequestParam(required = false)LocalDate date1, @RequestParam(required = false) LocalDate date2, @RequestParam(required = false) String type, @RequestParam Long accNo ){
       /* LocalDateTime localDateTime1= date1.atStartOfDay();
        LocalDateTime localDateTime2= LocalDateTime.of(date2, LocalTime.of(23,59,59));*/
        return transactionService.getAllTransactions(date1,date2,type,accNo);
    }


}
