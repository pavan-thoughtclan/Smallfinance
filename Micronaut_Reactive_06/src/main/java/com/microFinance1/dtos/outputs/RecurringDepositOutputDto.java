package com.microFinance1.dtos.outputs;

import com.microFinance1.utils.RdStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Introspected
@ToString
@Serdeable
public class RecurringDepositOutputDto {
    private UUID id;

    private String account;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

//    private List<Long> transactionIds;

    private RdStatus status ;

    private String interest;

}
