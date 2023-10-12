package com.smallfinance.repositories;

import com.smallfinance.entities.RecurringDeposit;
import com.smallfinance.entities.RecurringDepositPayment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringDepositPaymentRepository extends JpaRepository<RecurringDepositPayment, UUID> {
    List<RecurringDepositPayment> findAllByRecurringDeposit(RecurringDeposit r);
}
