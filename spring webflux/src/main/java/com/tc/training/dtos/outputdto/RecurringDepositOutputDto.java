package com.tc.training.dtos.outputdto;


import com.tc.training.utils.RdStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class RecurringDepositOutputDto
{

    private UUID id;

    private String account;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

    private RdStatus status ;

    private String interest;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
