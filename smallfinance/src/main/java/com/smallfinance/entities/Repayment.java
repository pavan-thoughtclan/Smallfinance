package com.smallfinance.entities;

import com.smallfinance.enums.PaymentStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Introspected
@Data
public class Repayment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id" , name = "loan_id")
    private Loan loan;

    private Integer monthNumber;

    private Double payAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private UUID transactionId;

    private Boolean penalty = false;
}
