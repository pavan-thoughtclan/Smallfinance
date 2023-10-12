package com.smallfinance.entities;

import com.smallfinance.enums.TransactionType;
import com.smallfinance.enums.TypeOfSlab;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
//    @GeneratedValue(GeneratedValue.Type.UUID)
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(referencedColumnName = "account_number")
    private AccountDetails from;

    @ManyToOne
    @JoinColumn(referencedColumnName = "account_number")
    private AccountDetails to;

    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "which_transaction")
    private TypeOfSlab whichTransaction;

    private String description = "The " + amount + " has been " + transactionType + " for " + whichTransaction;

    private Double balance;
}
