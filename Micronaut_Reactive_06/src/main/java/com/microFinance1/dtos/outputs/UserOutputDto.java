package com.microFinance1.dtos.outputs;

import com.microFinance1.utils.Role;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@Serdeable
public class UserOutputDto {
    private UUID userId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String email;

    private Integer age;

    private String aadharCardNumber;

    private String panCardNumber;

    private String phoneNumber;

    private Role roleName ;

    private Long accountNumber;

    private Boolean kyc;
}
