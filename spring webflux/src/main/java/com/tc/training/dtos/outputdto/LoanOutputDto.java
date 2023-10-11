package com.tc.training.dtos.outputdto;

import com.tc.training.utils.Status;
import com.tc.training.utils.TypeOfLoans;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;


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

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(Double loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public TypeOfLoans getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(TypeOfLoans typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Integer getMonthlyInterestAmount() {
        return monthlyInterestAmount;
    }

    public void setMonthlyInterestAmount(Integer monthlyInterestAmount) {
        this.monthlyInterestAmount = monthlyInterestAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
