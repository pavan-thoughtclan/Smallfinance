package com.tc.training.model;

import com.tc.training.utils.Status;
import com.tc.training.utils.TypeOfLoans;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;


@Table("loan")
public class Loan {

    @Id
    private UUID id;

    private LocalDate appliedDate;

    private LocalDate startDate;

    private Boolean isActive = Boolean.TRUE;

    private Status status = Status.UNDER_REVIEW;

    private Double loanedAmount;

    private  UUID slab_id;

    @Column("account_number")
    private Long account;

    @Enumerated(EnumType.STRING)
    private TypeOfLoans typeOfLoan ;

    private LocalDate loanEndDate;

    private String interest;

    private Double interestAmount;

    private Double totalAmount;

    private Double remainingAmount;

    private Integer monthlyInterestAmount;

    private LocalDate nextPaymentDate;

    private Integer missedPaymentCount = 0;

    private Integer totalMissedPayments=0;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(Double loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public UUID getSlab_id() {
        return slab_id;
    }

    public void setSlab_id(UUID slab_id) {
        this.slab_id = slab_id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public TypeOfLoans getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(TypeOfLoans typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Integer getMonthlyInterestAmount() {
        return monthlyInterestAmount;
    }

    public void setMonthlyInterestAmount(Integer monthlyInterestAmount) {
        this.monthlyInterestAmount = monthlyInterestAmount;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDate nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Integer getMissedPaymentCount() {
        return missedPaymentCount;
    }

    public void setMissedPaymentCount(Integer missedPaymentCount) {
        this.missedPaymentCount = missedPaymentCount;
    }

    public Integer getTotalMissedPayments() {
        return totalMissedPayments;
    }

    public void setTotalMissedPayments(Integer totalMissedPayments) {
        this.totalMissedPayments = totalMissedPayments;
    }
}
