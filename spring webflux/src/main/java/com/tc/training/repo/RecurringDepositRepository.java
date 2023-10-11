package com.tc.training.repo;

import com.tc.training.model.RecurringDeposit;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringDepositRepository extends ReactiveCrudRepository<RecurringDeposit, UUID> {

    @Query(value = "select * from recurring_deposit where status = 'ACTIVE'")
    Flux<RecurringDeposit> findByIsActive();

    @Query(value = "select * from recurring_deposit where account_number = :1")
    Flux<RecurringDeposit> findByAccount(Long accountNumber);

    @Query(value = "select * from recurring_deposit where account_number = :1 and status = :2")
    Flux<RecurringDeposit> findByAccountAndStatus(Long accNo, String rdStatus);
}
