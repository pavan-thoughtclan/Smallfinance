package com.microFinance1.entities;

import com.microFinance1.utils.Role;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.UUID;

@MappedEntity("users")
@Getter
@Setter
@ToString
@Introspected
public class User {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String password;

    private LocalDate dob;

    private String email;

    private Integer age;

    private String aadharCardNumber;

    private String panCardNumber;

    private String phoneNumber;

    private Role roleName = Role.ROLE_CUSTOMER;
}
