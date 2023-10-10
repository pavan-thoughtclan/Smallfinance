package com.microFinance1.repository;

import com.microFinance1.entities.Loan;
import com.microFinance1.utils.Status;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface LoanRepository extends ReactorCrudRepository<Loan, UUID> {
    @Query(value = "select * from loan where account_number = :accNo",nativeQuery = true)
    Flux<Loan> findAllByAccountNumber(Long accNo);

    @Query(value = "select * from loan where is_active = true",nativeQuery = true)
    Flux<Loan>findByIsActive();

    @Query(value = "select * from loan where account_number =:accNo and is_active = true",nativeQuery = true)
    Flux<Loan> findAllByAccountNumberAndIsActive(Long accNo);

    @Query(value = "select * from loan where status = 'UNDER_REVIEW' ",nativeQuery = true)
    Flux<Loan> findAllPending();

    @Query(value = "select * from loan where not status = 'UNDER_REVIEW' ",nativeQuery = true)
    Flux<Loan> findAllNotPending();
    @Query(value = "select * from loan where is_active = true and status = 'APPROVED'",nativeQuery = true)
    Flux<Loan> findByIsActiveAndAccepted();

    Flux<Loan> findByStatus(Status status);
}
