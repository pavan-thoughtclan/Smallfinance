package com.microFinance1.dtos.outputs;

import com.microFinance1.utils.Status;
import com.microFinance1.utils.TypeOfLoans;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Serdeable
@Introspected
public class LoanOutputDto {
    private UUID loanId;

    private String accountNumber;

    private LocalDate appliedDate;

    private Boolean isActive ;

    private String tenure;

    private Status status ;

    private Double loanedAmount;

    private TypeOfLoans typeOfLoan ;

    private LocalDate loanEndDate;

    private String interest;

    private Double interestAmount;

    private Integer monthlyInterestAmount;

    private Double totalAmount;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private Integer age;

    private String email;

}
