package com.microFinance1.dtos.inputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Introspected
@ToString
@Serdeable
public class RecurringDepositInputDto {
    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;
}
