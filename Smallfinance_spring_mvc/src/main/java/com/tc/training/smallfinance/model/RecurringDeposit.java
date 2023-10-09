package com.tc.training.smallfinance.model;


import com.tc.training.smallfinance.utils.RdStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class RecurringDeposit {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_number" , referencedColumnName = "accountNumber")
    private AccountDetails accountNumber;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

//    private List<Long> transactionIds;

    @Enumerated(EnumType.STRING)
    private RdStatus status = RdStatus.ACTIVE;

    private String interest;

    private  LocalDate nextPaymentDate;

    private Integer totalMissedPaymentCount =0;

    private Integer missedPayments = 0;
}
