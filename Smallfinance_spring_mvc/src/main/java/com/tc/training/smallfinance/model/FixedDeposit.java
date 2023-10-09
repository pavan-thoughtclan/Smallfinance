package com.tc.training.smallfinance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixedDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_number" , referencedColumnName = "accountNumber")
    private AccountDetails accountNumber;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name="slab_id")
    private Slabs slabs;

    //@Column(name = "pre_mature_withdrawal")
    private String interestRate;

    //@Column(name = "pre_mature_withdrawal")
    private Double amount;

    //@Column(name = "pre_mature_withdrawal")
    private LocalDate depositedDate;

    //@Column(name = "pre_mature_withdrawal")
    private LocalDate maturityDate;

    //@Column(name = "pre_mature_withdrawal")
    private Boolean isActive = Boolean.TRUE;

    //@Column(name = "pre_mature_withdrawal")
    private LocalDate preMatureWithdrawal = null;

    private Double totalAmount;

    private Double interestAmount = 0D;

//    private List<UUID> transactionIds = new ArrayList<>();

}
