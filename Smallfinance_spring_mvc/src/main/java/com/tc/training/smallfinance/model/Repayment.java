package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name="loan_id")
    private Loan loan;

    private Integer monthNumber;

    private Double payAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UPCOMING;

    private UUID transactionId;

    private Boolean penalty = false;


}
