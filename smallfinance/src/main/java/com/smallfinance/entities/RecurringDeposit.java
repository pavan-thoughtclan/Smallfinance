package com.smallfinance.entities;

import com.smallfinance.enums.RdStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Introspected
@Data
@Table(name = "recurring_deposit")
public class RecurringDeposit  {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;
    @ManyToOne
    @JoinColumn(name="account_number",referencedColumnName = "account_number")
    private AccountDetails accountNumber;

    @Column(name = "month_tenure")
    private Integer monthTenure;

    @Column(name="monthly_paid_amount")
    private Double monthlyPaidAmount;

    @Column(name="maturity_amount")
    private Double maturityAmount;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="maturity_date")
    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    private RdStatus status = RdStatus.ACTIVE;

    private String interest;

    @Column(name = "next_payment_date")
    private LocalDate nextPaymentDate;

    @Column(name = "total_missed_payment_count")
    private Integer totalMissedPaymentCount;

    @Column(name = "missed_payments")
    private Integer missedPayments;

    @OneToMany(mappedBy = "recurringDeposit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecurringDepositPayment> rdPayments;
}
