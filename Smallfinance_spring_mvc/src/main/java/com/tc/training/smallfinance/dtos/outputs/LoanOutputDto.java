package com.tc.training.smallfinance.dtos.outputs;

import com.tc.training.smallfinance.utils.Status;
import com.tc.training.smallfinance.utils.TypeOfLoans;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
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
