package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;
import com.tc.training.smallfinance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public TransactionOutputDto transfer(@RequestBody TransactionInputDto transactionInputDto, @RequestParam Long accNo){

        return transactionService.deposit(transactionInputDto,accNo);
    }

    @GetMapping("/all_transactions")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<TransactionOutputDto> transactionHistory(@RequestParam(required = false)LocalDate date1, @RequestParam(required = false) LocalDate date2,@RequestParam(required = false) String type, @RequestParam Long accNo ){
       /* LocalDateTime localDateTime1= date1.atStartOfDay();
        LocalDateTime localDateTime2= LocalDateTime.of(date2, LocalTime.of(23,59,59));*/
        return transactionService.getAllTransactions(date1,date2,type,accNo);
    }


}
