package com.tc.training.dtos.outputdto;

import lombok.Data;

@Data
public class HomePageOutputDto {

    private Boolean kyc;

    private Double balance;

    private Double depositAmount;

    private Double loanAmount;

    public Boolean getKyc() {
        return kyc;
    }

    public void setKyc(Boolean kyc) {
        this.kyc = kyc;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }
}
