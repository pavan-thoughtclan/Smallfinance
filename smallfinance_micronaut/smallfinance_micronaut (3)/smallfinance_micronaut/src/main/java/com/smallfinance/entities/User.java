package com.smallfinance.entities;

import com.smallfinance.enums.Role;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Introspected
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;
    @NotBlank
    @Size(min = 1, message = "Please enter name with at least 3 characters")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    private String password;

    private LocalDate dob;

    @Email
    private String email;

    private Integer age;
    @Column(name = "aadhar_card_number")
    private String aadharCardNumber;

    @Column(name = "pan_card_number")
    private String panCardNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role roleName;

}
