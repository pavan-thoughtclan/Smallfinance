package com.microFinance1.repository;

import com.microFinance1.entities.AccountDetails;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface AccountRepository extends ReactorCrudRepository<AccountDetails,Long> {
//    Mono<AccountDetails> findByUser(User user);
    @Query(value = "select * from account_details where user_id = :userId",nativeQuery = true)
    Mono<AccountDetails> findByUserID(UUID userId);

}
