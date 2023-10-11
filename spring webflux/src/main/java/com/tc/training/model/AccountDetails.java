package com.tc.training.model;

import com.tc.training.utils.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_details")
public class AccountDetails implements Persistable<Long> {


    @Id
    private Long accountNumber;

    private AccountType accountType = AccountType.Savings;

    private LocalDate openingDate ;

    private LocalDate closingDate;

    private Double balance = 0D;

    private Boolean kyc = Boolean.FALSE;

    private UUID user_id;

    @Transient
    private Boolean isNew = Boolean.FALSE;


    @Transient
    public Boolean getNew() {
        return isNew;
    }
    @Transient
    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getKyc() {
        return kyc;
    }

    public void setKyc(Boolean kyc) {
        this.kyc = kyc;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }


    @Override
    public Long getId() {
        return accountNumber;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
