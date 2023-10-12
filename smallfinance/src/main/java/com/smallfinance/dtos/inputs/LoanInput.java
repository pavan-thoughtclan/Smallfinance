package com.smallfinance.dtos.inputs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoanInput {
    private String accountNumber;

    private Double loanAmount;

    private String type;

    private String tenure;

}
