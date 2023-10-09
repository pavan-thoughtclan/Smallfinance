package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class RecurringDepositPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name="recurringDeposit_id")
    private RecurringDeposit recurringDeposit;

    private Integer monthNumber;

    private Double payAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UPCOMING;

    private UUID transactionId;


}
