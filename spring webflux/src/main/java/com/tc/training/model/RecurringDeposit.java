package com.tc.training.model;


import com.tc.training.utils.RdStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table("recurring_deposit")
public class RecurringDeposit {


    @Id
    private UUID id;

    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    private RdStatus status = RdStatus.ACTIVE;

    private String interest;

    private  LocalDate nextPaymentDate;

    private Integer totalMissedPaymentCount =0;

    private Integer missedPayments = 0;

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

    public Integer getMonthTenure() {
        return monthTenure;
    }

    public void setMonthTenure(Integer monthTenure) {
        this.monthTenure = monthTenure;
    }

    public Double getMonthlyPaidAmount() {
        return monthlyPaidAmount;
    }

    public void setMonthlyPaidAmount(Double monthlyPaidAmount) {
        this.monthlyPaidAmount = monthlyPaidAmount;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public RdStatus getStatus() {
        return status;
    }

    public void setStatus(RdStatus status) {
        this.status = status;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDate nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Integer getTotalMissedPaymentCount() {
        return totalMissedPaymentCount;
    }

    public void setTotalMissedPaymentCount(Integer totalMissedPaymentCount) {
        this.totalMissedPaymentCount = totalMissedPaymentCount;
    }

    public Integer getMissedPayments() {
        return missedPayments;
    }

    public void setMissedPayments(Integer missedPayments) {
        this.missedPayments = missedPayments;
    }
}
