package com.microFinance1.entities;

import com.microFinance1.utils.AccountType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@MappedEntity("account_details")
@Getter
@Setter
@ToString
@Introspected
public class AccountDetails {
    @Id
    private Long accountNumber;

    private AccountType accountType = AccountType.Savings;

    private LocalDate openingDate;

    private LocalDate closingDate;

    private Double balance = 0D;

    private Boolean kyc = Boolean.FALSE;

    private UUID userId;
}


