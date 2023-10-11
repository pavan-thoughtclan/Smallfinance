package com.tc.training.dtos.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixedDepositInputDto {

    private Long accountNumber;

    private String tenures;

    private Double amount;


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTenures() {
        return tenures;
    }

    public void setTenures(String tenures) {
        this.tenures = tenures;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
