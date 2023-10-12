package com.smallfinance.dtos.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsInput {

    private String firstName;

    private String lastName;

    private LocalDate dob;


    private String aadharCardNumber;

    private String panCardNumber;

    private LocalDate openingDate;

    private LocalDate closingDate;

    private String phoneNumber;

    @Email
    private String email;
}
