package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.TransactionType;
import com.tc.training.smallfinance.utils.TypeOfSlab;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "accountNumber")
    private AccountDetails from;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "accountNumber")
    private AccountDetails to;


   /* @ManyToOne
    @JoinColumn(referencedColumnName = "accountNumber")
    private AccountDetails accountNumber;*/


    private LocalDateTime timestamp;


    @Enumerated(EnumType.STRING)
    private TypeOfSlab whichTransaction;

    private String description = "The "+amount+" has been "+transactionType+" for "+whichTransaction;

    private Double balance;

}
