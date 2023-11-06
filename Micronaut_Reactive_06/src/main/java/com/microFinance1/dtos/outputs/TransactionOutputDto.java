package com.microFinance1.dtos.outputs;

import com.microFinance1.utils.TransactionType;
import com.microFinance1.utils.TypeOfSlab;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Serdeable
public class TransactionOutputDto {
    private UUID transactionID;

    private Double amount;

    private TransactionType transactionType;

    private String fromAccountNumber;

    private String toAccountNumber;

    private LocalDateTime timestamp;

    private TypeOfSlab whichTransaction;

    private Double balance;

    private String description = "The "+amount+" has been "+transactionType+" for "+whichTransaction;
}
