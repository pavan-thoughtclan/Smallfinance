package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
@ToString(exclude = {"accountDetails"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(min=3,message = "Please enter name with atleast 3 characters")
    private String firstName;

    private String lastName;

    @NotBlank
    private String password;

    private LocalDate dob;

    @Email
    private String email;

    private Integer age;

    private String aadharCardNumber;

    private String panCardNumber;

    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Role roleName = Role.ROLE_CUSTOMER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AccountDetails> accountDetails= new ArrayList<>();
}
