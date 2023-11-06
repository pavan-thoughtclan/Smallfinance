package com.microFinance1.dtos.outputs;

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
public class FDDetails {

    private Double totalFdAmount=0D;

    private Double totalRdAmount=0D;

    private Integer totalNoOfRD=0;

    private Integer totalNoOfFD=0;
}
