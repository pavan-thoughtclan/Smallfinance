package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "account_details")
public class AccountDetails {

    @Id
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
//    @Column(name = "account_type")
//    @ColumnDefault("'Savings'")
    private AccountType accountType = AccountType.Savings;

    private LocalDate openingDate ;

    private LocalDate closingDate;

    @Column(name = "balance",columnDefinition = "Bigint default 0.0")
//    @ColumnDefault("0.0")
    private Double balance = 0D;

//    @Column(name = "kyc")
//    @ColumnDefault("false")
    private Boolean kyc=Boolean.FALSE;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name="user_id")
    private User user;

}
