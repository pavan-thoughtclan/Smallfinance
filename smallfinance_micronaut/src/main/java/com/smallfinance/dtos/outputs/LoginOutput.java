package com.smallfinance.dtos.outputs;

import com.smallfinance.enums.Role;
import lombok.Data;

@Data
public class LoginOutput {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private Double balance;

    private Double loanAmount = 0D;

    private Double depositAmount=0D;

    private Long accNo;

    private Boolean kyc;

    private String aadharCardNumber;

    private String panCardNumber;

    private Role roleName;

    private String accessToken;

    private String refreshToken;

    private String expiresIn;
}
