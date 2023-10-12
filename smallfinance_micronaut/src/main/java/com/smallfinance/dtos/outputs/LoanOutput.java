package com.smallfinance.dtos.outputs;

import com.smallfinance.enums.Status;
import com.smallfinance.enums.TypeOfLoans;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class LoanOutput {
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

    private String loanSuppliment1;

    private String loanSuppliment2;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private Integer age;

    private String email;
}
