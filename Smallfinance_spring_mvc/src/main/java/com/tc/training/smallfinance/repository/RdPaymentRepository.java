package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.RecurringDeposit;
import com.tc.training.smallfinance.model.RecurringDepositPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RdPaymentRepository extends JpaRepository<RecurringDepositPayment, UUID> {

    List<RecurringDepositPayment> findAllByRecurringDeposit(RecurringDeposit r);
}
