package com.microFinance1.entities;

import com.microFinance1.utils.PaymentStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@MappedEntity
@Getter
@Setter
@ToString
@Introspected
public class RecurringDepositPayment {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private UUID recurringDepositId;

    private Integer monthNumber;

    private Double payAmount;

    private PaymentStatus paymentStatus = PaymentStatus.UPCOMING;

    private UUID transactionId;
}
