package com.microFinance1.dtos.outputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Serdeable
public class HomePageOutputDto {
    private Boolean kyc;

    private Double balance;

    private Double depositAmount;

    private Double loanAmount;
}
