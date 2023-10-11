package com.tc.training.dtos.inputdto;

import lombok.Getter;
import lombok.Setter;


public class SlabInputDto {

    private String tenures;

    private String interestRate;

    private String typeOfTransaction;

    public String getTenures() {
        return tenures;
    }

    public void setTenures(String tenures) {
        this.tenures = tenures;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }
}
