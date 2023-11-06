package com.microFinance1.repository;

import com.microFinance1.entities.FixedDeposit;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface FixedDepositRepository extends ReactorCrudRepository<FixedDeposit, UUID> {
    @Query(value = "SELECT * FROM fixed_deposit WHERE account_number= :accountNumber",nativeQuery = true)
    Flux<FixedDeposit> findByAccountNumber(Long accountNumber);

    @Query(value = "select * from fixed_deposit where is_active = true",nativeQuery = true)
    Flux<FixedDeposit> findAllByIsActive();

    @Query(value = "select * from fixed_deposit where is_active = true and account_number =:accNo",nativeQuery = true)
    Flux<FixedDeposit>findByAccountNumberAndIsActive(Long accNo);
}
