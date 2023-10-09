package com.tc.training.smallfinance.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixedDepositInputDto {

    private Long accountNumber;

    private String tenures;

    private Double amount;
}
