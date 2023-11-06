package com.microFinance1.entities;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@MappedEntity("fixed_deposit")
@Getter
@Setter
@ToString
@Introspected
public class FixedDeposit {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private Long accountNumber;

    private UUID slabId;

    private String interestRate;

    private Double amount;

    private LocalDate depositedDate;

    private LocalDate maturityDate;

    private Boolean isActive = Boolean.TRUE;

    private LocalDate preMatureWithdrawal = null;

    private Double totalAmount;

    private Double interestAmount = 0D;
}
