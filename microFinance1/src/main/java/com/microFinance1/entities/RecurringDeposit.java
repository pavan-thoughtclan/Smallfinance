package com.microFinance1.entities;

import com.microFinance1.utils.RdStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@MappedEntity("recurring_deposit")
@Getter
@Setter
@ToString
@Introspected
@Serdeable
public class RecurringDeposit {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private Long accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

    private RdStatus status = RdStatus.ACTIVE;

    private String interest;

    private  LocalDate nextPaymentDate;

    private Integer totalMissedPaymentCount =0;

    private Integer missedPayments = 0;

}
