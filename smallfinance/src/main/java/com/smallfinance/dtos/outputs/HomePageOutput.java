package com.smallfinance.dtos.outputs;

import lombok.Data;

@Data
public class HomePageOutput {
    private Boolean kyc;

    private Double balance;

    private Double depositAmount;

    private Double loanAmount;

}
