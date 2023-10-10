package com.microFinance1.repository;

import com.microFinance1.entities.User;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;

import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends ReactorCrudRepository<User, UUID> {

}
