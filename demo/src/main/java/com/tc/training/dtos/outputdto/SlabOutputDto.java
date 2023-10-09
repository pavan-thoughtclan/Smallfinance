package com.tc.training.dtos.outputdto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public class SlabOutputDto {

    private UUID slabId;

    private String tenures;

    private String interestRate;

    private String typeOfTransaction;

    public UUID getSlabId() {
        return slabId;
    }

    public void setSlabId(UUID slabId) {
        this.slabId = slabId;
    }

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
