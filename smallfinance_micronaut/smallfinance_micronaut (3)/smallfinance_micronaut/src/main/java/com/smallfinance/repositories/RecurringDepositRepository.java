package com.smallfinance.repositories;

import com.smallfinance.entities.RecurringDeposit;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.http.annotation.QueryValue;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringDepositRepository extends JpaRepository<RecurringDeposit, UUID> {
    @Query(value = "select * from recurring_deposit where status = 'ACTIVE'",nativeQuery = true)
    List<RecurringDeposit> findByIsActive();

    @Query(value = "select * from recurring_deposit where account_account_number = ?1",nativeQuery = true)
    List<RecurringDeposit> findByAccount(Long accountNumber);

    @Query(value = "select * from recurring_deposit where account_number = :accNo and status = :rdStatus",nativeQuery = true)
    List<RecurringDeposit> findByAccountAndStatus(Long accNo, String rdStatus);

}
