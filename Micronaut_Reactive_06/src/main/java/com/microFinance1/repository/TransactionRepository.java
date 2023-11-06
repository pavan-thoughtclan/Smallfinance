package com.microFinance1.repository;

import com.microFinance1.entities.Transaction;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import io.micronaut.http.annotation.QueryValue;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface TransactionRepository extends ReactorCrudRepository<Transaction, UUID> {
    /*@Query(value = "select * from transactions t where (t.from_account_number = ?3 OR t.to_account_number = ?3) and t.timestamp between ?1 and ?2",nativeQuery = true)
    Flux<Transaction> findAllByUserAndDate(LocalDateTime localDateTime1, LocalDateTime localDateTime2, Long accNo);
}*/
    @Query(value = "select * from transactions t where (t.from_account_number = :accNo OR t.to_account_number = :accNo) and t.timestamp between :localDateTime1 and :localDateTime2")
    //
    Flux<Transaction> findAllByUserAndDate(
            @QueryValue("localDateTime1") LocalDateTime localDateTime1,
            @QueryValue("localDateTime2") LocalDateTime localDateTime2,
            @QueryValue("accNo") Long accNo
    );
}
