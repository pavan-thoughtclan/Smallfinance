package com.microFinance1.dtos.outputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@Serdeable
@Introspected
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
