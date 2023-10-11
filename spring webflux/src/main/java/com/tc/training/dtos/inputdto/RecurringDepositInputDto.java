package com.tc.training.dtos.inputdto;

import lombok.Data;


public class RecurringDepositInputDto {

    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

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
}
