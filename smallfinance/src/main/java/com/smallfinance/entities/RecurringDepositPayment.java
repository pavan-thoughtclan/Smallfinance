package com.smallfinance.entities;

import com.smallfinance.enums.PaymentStatus;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Introspected
@Table(name = "recurring_deposit_payment")
public class RecurringDepositPayment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "recurring_deposit_id")
    private RecurringDeposit recurringDeposit;

    @Column(name = "month_number")
    private Integer monthNumber;

    @Column(name="pay_amount")
    private Double payAmount;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private UUID transactionId;
}
