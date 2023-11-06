package com.microFinance1.dtos.outputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Introspected
@Serdeable
public class SlabOutputDto {

    private UUID slabId;

    private String tenures;

    private String  interestRate;

    private String typeOfTransaction;
}
