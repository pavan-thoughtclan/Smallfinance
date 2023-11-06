package com.microFinance1.dtos.outputs;

import com.microFinance1.utils.AccountType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Serdeable
public class AccountDetailsOutputDto {
    private Long accountNumber;

    private AccountType accountType;

    private String email;

    private Long balance;

    private Boolean kyc;
}
