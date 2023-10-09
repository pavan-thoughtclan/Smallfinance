package com.tc.training.smallfinance.dtos.inputs;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailsInputDto {

    private String firstName;

    private String lastName;

   // private AccountType accountType;

    private LocalDate dob;


    private String aadharCardNumber;

    private String panCardNumber;

    private LocalDate openingDate;

    private LocalDate closingDate;
    @UniqueElements
    private String phoneNumber;

    @Email
    private String email;

}
