package com.smallfinance.services;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.QueryValue;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionOutput deposit(@Body TransactionInput transactionInputDto, @QueryValue Long accountNumber);

    List<TransactionOutput> getAllTransactions(@QueryValue LocalDate date1, @QueryValue LocalDate date2,@QueryValue String type,@QueryValue Long accNo);

}
