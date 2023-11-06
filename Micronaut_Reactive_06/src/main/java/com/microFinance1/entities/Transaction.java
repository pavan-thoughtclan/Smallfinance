package com.microFinance1.entities;

import com.microFinance1.utils.TransactionType;
import com.microFinance1.utils.TypeOfSlab;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedEntity("transactions")
@Getter
@Setter
@ToString
@Introspected
public class Transaction {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private Double amount;

    private TransactionType transactionType;
    
    private Long fromAccountNumber;
    
    private Long toAccountNumber;

    private LocalDateTime timestamp;

    private TypeOfSlab whichTransaction;

    private String description = "The "+amount+" has been "+transactionType+" for "+whichTransaction;

    private Double balance;
}
