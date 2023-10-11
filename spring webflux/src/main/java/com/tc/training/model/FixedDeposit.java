package com.tc.training.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "fixed_deposit")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixedDeposit {

    @Id
    private UUID id;

    private Long accountNumber;

    private UUID slab_id;

    private String interestRate;

    private Double amount;

    private LocalDate depositedDate;

    private LocalDate maturityDate;

    private Boolean isActive = Boolean.TRUE;

    private LocalDate preMatureWithdrawal = null;

    private Double totalAmount;

    private Double interestAmount = 0D;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UUID getSlab_id() {
        return slab_id;
    }

    public void setSlab_id(UUID slab_id) {
        this.slab_id = slab_id;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDepositedDate() {
        return depositedDate;
    }

    public void setDepositedDate(LocalDate depositedDate) {
        this.depositedDate = depositedDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getPreMatureWithDrawl() {
        return preMatureWithdrawal;
    }

    public void setPreMatureWithDrawl(LocalDate preMatureWithDrawl) {
        this.preMatureWithdrawal = preMatureWithDrawl;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }
}
