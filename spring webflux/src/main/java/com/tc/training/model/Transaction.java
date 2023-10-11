package com.tc.training.model;

import com.tc.training.utils.TransactionType;
import com.tc.training.utils.TypeOfSlab;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    private UUID id;

    private Double amount;

    @Enumerated(EnumType.STRING)
   private TransactionType transactionType;

    @Column("from_account_number")
    private Long from_id;

    @Column("to_account_number")
    private Long to_id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TypeOfSlab whichTransaction;

    private String description = "The "+amount+" has been "+transactionType+" for "+whichTransaction;

    private Double balance;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getFrom_id() {
        return from_id;
    }

    public void setFrom_id(Long from_id) {
        this.from_id = from_id;
    }

    public Long getTo_id() {
        return to_id;
    }

    public void setTo_id(Long to_id) {
        this.to_id = to_id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TypeOfSlab getWhichTransaction() {
        return whichTransaction;
    }

    public void setWhichTransaction(TypeOfSlab whichTransaction) {
        this.whichTransaction = whichTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
