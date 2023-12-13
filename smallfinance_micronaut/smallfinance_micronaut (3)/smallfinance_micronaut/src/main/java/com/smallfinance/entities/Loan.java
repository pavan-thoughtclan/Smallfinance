package com.smallfinance.entities;

import com.smallfinance.enums.Status;
import com.smallfinance.enums.TypeOfLoans;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Introspected
@Data
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)

    private UUID id;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "loaned_amount")
    private Double loanedAmount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "slab_id")
    private Slabs slab;

    @ManyToOne
    @JoinColumn(name="account_number",referencedColumnName = "account_number")
    private AccountDetails accountNumber;

    @Column(name = "type_of_loan")
    @Enumerated(EnumType.STRING)
    private TypeOfLoans typeOfLoan;

    @Column(name = "loan_end_date")
    private LocalDate loanEndDate;

    private String interest;

    @Column(name = "interest_amount")
    private Double interestAmount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "remaining_amount")
    private Double remainingAmount;

    @Column(name = "monthly_interest_amount")
    private Integer monthlyInterestAmount;

    @Column(name = "next_payment_date")
    private LocalDate nextPaymentDate;

    @Column(name = "missed_payment_count")
    private Integer missedPaymentCount;

    @Column(name = "total_missed_payments")
    private Integer totalMissedPayments;
}
