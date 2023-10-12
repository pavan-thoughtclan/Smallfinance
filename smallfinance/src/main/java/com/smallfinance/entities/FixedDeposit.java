package com.smallfinance.entities;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@Table(name = "fixed_deposit")
public class FixedDeposit{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_number",referencedColumnName = "account_number")
    private AccountDetails accountNumber;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "slab_id")
    private Slabs slabs;

    @Column(name = "interest_rate")
    private String interestRate;

    private Double amount;

    @Column(name = "deposited_date")
    private LocalDate depositedDate;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "pre_mature_withdrawal")
    private LocalDate preMatureWithDrawl = null;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "interest_amount")
    private Double interestAmount = 0D;

}
