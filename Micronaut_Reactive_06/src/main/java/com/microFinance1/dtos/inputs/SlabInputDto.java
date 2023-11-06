package com.microFinance1.dtos.inputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Serdeable
@Introspected
public class SlabInputDto {

    private String tenures;

    private String interestRate;

    private String typeOfTransaction;
}
