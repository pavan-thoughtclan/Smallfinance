package com.tc.training.repo;


import com.tc.training.model.FixedDeposit;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedDepositRepository extends ReactiveCrudRepository<FixedDeposit, UUID> {

    @Query(value = "select * from fixed_deposit where account_number = :1")
    Flux<FixedDeposit> findByAccountNumber(Long accountNumber);
    @Query(value = "select * from fixed_deposit where is_active = true")
    Flux<FixedDeposit> findAllByIsActive();
    @Query(value = "select * from fixed_deposit where is_active = true and account_number = :1")
    Flux<FixedDeposit> findByAccountNumberAndIsActive(Long accNo);
}
