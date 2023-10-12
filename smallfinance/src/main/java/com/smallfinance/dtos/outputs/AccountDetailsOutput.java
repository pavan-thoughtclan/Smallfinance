package com.smallfinance.dtos.outputs;

import com.smallfinance.enums.AccountType;
import lombok.Data;

@Data
public class AccountDetailsOutput {
    private Long accountNumber;

    private AccountType accountType;

    private String email;

    private Long balance;

    private Boolean kyc;
}
