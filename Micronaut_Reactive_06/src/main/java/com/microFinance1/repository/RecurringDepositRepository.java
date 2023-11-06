package com.microFinance1.repository;

import com.microFinance1.entities.RecurringDeposit;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface RecurringDepositRepository extends ReactorCrudRepository<RecurringDeposit, UUID> {

    @Query(value = "select * from recurring_deposit where status = 'ACTIVE'",nativeQuery = true)
    Flux<RecurringDeposit> findByIsActive();

    @Query(value = "select * from recurring_deposit where account_number = :accountNumber",nativeQuery = true)
    Flux<RecurringDeposit> findByAccount(Long accountNumber);

    @Query(value = "select * from recurring_deposit where account_number = :accountNumber AND status ='ACTIVE'",nativeQuery = true)
    Flux<RecurringDeposit> findByAccountAndStatus(Long accountNumber, String rdStatus);
}
