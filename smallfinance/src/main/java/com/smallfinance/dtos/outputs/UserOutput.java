package com.smallfinance.dtos.outputs;

import com.smallfinance.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserOutput {
    private UUID userId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String email;

    private Integer age;

    private String aadharCardNumber;

    private String panCardNumber;

    private String phoneNumber;

    private String aadharPhoto;

    private String panPhoto;


    private String  userPhoto;


    private Role roleName ;

    private String salarySlip;

    private String homeSlip;

    private Long accountNumber;

    private Boolean kyc;

}
