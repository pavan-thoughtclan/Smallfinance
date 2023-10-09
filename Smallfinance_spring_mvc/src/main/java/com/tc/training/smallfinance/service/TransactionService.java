package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionOutputDto deposit(TransactionInputDto transactionInputDto,Long accountNumber);

    List<TransactionOutputDto> getAllTransactions(LocalDate date1, LocalDate date2,String type, Long accNo);
}
