package com.tc.training.dtos.outputdto;

import lombok.Data;


public class FDDetails {

    private Double totalFdAmount = 0D;

    private Double totalRdAmount = 0D;

    private Integer totalNoOfRD = 0;

    private Integer totalNoOfFD = 0;

    public Double getTotalFdAmount() {
        return totalFdAmount;
    }

    public void setTotalFdAmount(Double totalFdAmount) {
        this.totalFdAmount = totalFdAmount;
    }

    public Double getTotalRdAmount() {
        return totalRdAmount;
    }

    public void setTotalRdAmount(Double totalRdAmount) {
        this.totalRdAmount = totalRdAmount;
    }

    public Integer getTotalNoOfRD() {
        return totalNoOfRD;
    }

    public void setTotalNoOfRD(Integer totalNoOfRD) {
        this.totalNoOfRD = totalNoOfRD;
    }

    public Integer getTotalNoOfFD() {
        return totalNoOfFD;
    }

    public void setTotalNoOfFD(Integer totalNoOfFD) {
        this.totalNoOfFD = totalNoOfFD;
    }
}
