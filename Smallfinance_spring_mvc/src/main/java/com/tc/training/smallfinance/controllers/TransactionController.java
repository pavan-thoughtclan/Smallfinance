package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;
import com.tc.training.smallfinance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * TransactionController handles operations related to transactions APIs.
 */

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * Perform a synchronous transfer transaction and return the transaction details.
     * @param transactionInputDto TransactionInput
     * @param accNo Account number for the transaction
     * @return TransactionOutput
     */

//    @PostMapping("/transfer")
    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public TransactionOutputDto transfer(@RequestBody TransactionInputDto transactionInputDto, @RequestParam Long accNo)throws Exception{
        return transactionService.deposit(transactionInputDto,accNo);
    }

    /**
     * Get a list of all transactions within a specified date range and of a specific type for an account.
     * @param date1 Start date of the date range
     * @param date2 End date of the date range
     * @param type Type of the transaction
     * @param accNo Account number for the transactions
     * @return List of TransactionOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<TransactionOutputDto> transactionHistory(@RequestParam(required = false)LocalDate date1, @RequestParam(required = false) LocalDate date2,@RequestParam(required = false) String type, @RequestParam Long accNo )throws Exception{
        return transactionService.getAllTransactions(date1,date2,type,accNo);
    }
}
