package com.microFinance1.dtos.inputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Introspected
@Serdeable
public class FixedDepositInputDto {

    private Long accountNumber;

    private String tenures;

    private Double amount;
}
