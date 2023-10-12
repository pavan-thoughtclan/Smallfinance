package com.smallfinance.dtos.outputs;

import com.smallfinance.enums.TransactionType;
import com.smallfinance.enums.TypeOfSlab;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionOutput {
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
