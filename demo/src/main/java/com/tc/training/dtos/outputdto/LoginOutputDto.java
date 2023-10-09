package com.tc.training.dtos.outputdto;


import com.tc.training.utils.Role;
import lombok.Data;

@Data
public class LoginOutputDto {

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

   // private List<TransactionOutputDto> transactions;
}
