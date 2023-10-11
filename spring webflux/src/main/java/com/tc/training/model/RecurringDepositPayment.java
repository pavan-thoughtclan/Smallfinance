package com.tc.training.model;

import com.tc.training.utils.PaymentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("recurring_deposit_payment")
public class RecurringDepositPayment {

    @Id
    private UUID id;

    private UUID recurringDeposit_id;

    private Integer monthNumber;

    private Double payAmount;

    private PaymentStatus paymentStatus = PaymentStatus.UPCOMING;

    private UUID transactionId;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRecurringDeposit_id() {
        return recurringDeposit_id;
    }

    public void setRecurringDeposit_id(UUID recurringDeposit_id) {
        this.recurringDeposit_id = recurringDeposit_id;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
