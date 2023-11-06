package com.microFinance1.dtos.inputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Serdeable
@Introspected
public class LoanInputDto {

    private String accountNumber;

    private Double loanAmount;

    private String type;

    private String tenure;
}
