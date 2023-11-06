package com.microFinance1.repository;

import com.microFinance1.entities.Slabs;
import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfSlab;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SlabRepository extends ReactorCrudRepository<Slabs, UUID> {
    @Query(value = "select * from slabs s where s.tenures=:tenures AND s.type_of_transaction=:typeOfTransaction", nativeQuery = true)
    Mono<Slabs> findByTenuresAndTypeOfTransaction(Tenures tenures, TypeOfSlab typeOfTransaction);
}

