package com.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class LoginInput {
    private String accountNumber;

    private String password;
}
