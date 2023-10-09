package com.tc.training.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class LoanInputDto {

    private String accountNumber;

    private Double loanAmount;

    private String type;

    private String tenure;

}
