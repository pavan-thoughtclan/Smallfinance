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
public class TransactionInputDto {
    private Long to;

    private Double amount;

    private String purpose;

    private Long accountNumber;

    private String type;

    private String trans;
}
