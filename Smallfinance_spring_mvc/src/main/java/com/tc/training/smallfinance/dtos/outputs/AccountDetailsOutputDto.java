package com.tc.training.smallfinance.dtos.outputs;

import com.tc.training.smallfinance.utils.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsOutputDto {

    private Long accountNumber;

    private AccountType accountType;

    private String email;

    private Long balance;

    private Boolean kyc;


}
