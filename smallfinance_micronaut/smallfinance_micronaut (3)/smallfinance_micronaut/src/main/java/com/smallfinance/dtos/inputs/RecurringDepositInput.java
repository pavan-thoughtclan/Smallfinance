package com.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class RecurringDepositInput {
    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;
}
