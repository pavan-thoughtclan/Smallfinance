package com.smallfinance.dtos.inputs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FixedDepositInput {
    private Long accountNumber;

    private String tenures;

    private Double amount;
}
