package com.tc.training.dtos.outputdto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;


public class FixedDepositOutputDto {

    private UUID fdId;

    private Double amount;

    private String interestRate;

    private Boolean isActive;

    private Long accountNumber;

    private LocalDate maturityDate;

    private Double interestAmountAdded;

    private Double totalAmount;


    public UUID getFdId() {
        return fdId;
    }

    public void setFdId(UUID fdId) {
        this.fdId = fdId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Double getInterestAmountAdded() {
        return interestAmountAdded;
    }

    public void setInterestAmountAdded(Double interestAmountAdded) {
        this.interestAmountAdded = interestAmountAdded;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
