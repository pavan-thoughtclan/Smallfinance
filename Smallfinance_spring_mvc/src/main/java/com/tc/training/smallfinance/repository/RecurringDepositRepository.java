package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.RecurringDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringDepositRepository extends JpaRepository<RecurringDeposit, UUID> {

    @Query(value = "select * from recurring_deposit where status = 'ACTIVE'",nativeQuery = true)
    List<RecurringDeposit> findByIsActive();

    @Query(value = "select * from recurring_deposit where account_account_number = ?1",nativeQuery = true)
    List<RecurringDeposit> findByAccount(Long accountNumber);

    @Query(value = "select * from recurring_deposit where account_number = ?1 and status = ?2",nativeQuery = true)
    List<RecurringDeposit> findByAccountAndStatus(Long accNo, String rdStatus);
}
