package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.Status;
import com.tc.training.smallfinance.utils.TypeOfLoans;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate appliedDate;

    private LocalDate startDate;

    private Boolean isActive = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    private Status status = Status.UNDER_REVIEW;

    private Double loanedAmount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name="slab_id")
    private  Slabs slab;

    @ManyToOne
    @JoinColumn(name = "account_number" , referencedColumnName = "accountNumber")
    private AccountDetails accountNumber;

    @Enumerated(EnumType.STRING)
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
