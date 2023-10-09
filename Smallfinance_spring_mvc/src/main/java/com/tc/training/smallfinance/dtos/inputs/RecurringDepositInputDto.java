package com.tc.training.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class RecurringDepositInputDto {

    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

}
