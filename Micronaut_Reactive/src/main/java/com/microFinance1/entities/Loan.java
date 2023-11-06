package com.microFinance1.entities;

import com.microFinance1.utils.Status;
import com.microFinance1.utils.TypeOfLoans;
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

@MappedEntity("loan")
@Getter
@Setter
@ToString
@Introspected
public class Loan {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private LocalDate appliedDate;

    private LocalDate startDate;

    private Boolean isActive = Boolean.TRUE;

    private Status status = Status.UNDER_REVIEW;

    private Double loanedAmount;

    private  UUID slabId;

    private Long accountNumber;

    private TypeOfLoans typeOfLoan ;

    private LocalDate loanEndDate;

    private String interest;

    private Double interestAmount;

    private Double totalAmount;

    private Double remainingAmount;

    private Integer monthlyInterestAmount;

    private LocalDate nextPaymentDate;

    private Integer missedPaymentCount = 0;

    private Integer totalMissedPayments=0;
}
