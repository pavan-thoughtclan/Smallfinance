package com.tc.training.smallfinance.dtos.outputs;

import lombok.Data;

@Data
public class HomePageOutputDto {

    private Boolean kyc;

    private Double balance;

    private Double depositAmount;

    private Double loanAmount;


}
