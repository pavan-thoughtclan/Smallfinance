package com.tc.training.smallfinance.dtos.outputs;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class FixedDepositOutputDto {

    private UUID fdId;

    private Double amount;

    private String interestRate;

    private Boolean isActive;

    private Long accountNumber;

    private LocalDate maturityDate;

    private Double interestAmountAdded;

    private Double totalAmount;

}
