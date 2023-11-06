package com.microFinance1.repository;

import com.microFinance1.entities.RecurringDepositPayment;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface RecurringDepositPaymentRepository extends ReactorCrudRepository<RecurringDepositPayment, UUID> {
    @Query(value = "select * from recurring_deposit_payment where recurring_deposit_id = :ids ",nativeQuery = true)
    public Flux<RecurringDepositPayment> findByRecurringDepositId(UUID ids);

}
