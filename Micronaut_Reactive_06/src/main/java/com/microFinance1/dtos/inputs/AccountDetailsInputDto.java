package com.microFinance1.dtos.inputs;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.beans.Transient;
import java.time.LocalDate;
@Getter
@Setter
@ToString
@Introspected
@Serdeable
public class AccountDetailsInputDto {
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
