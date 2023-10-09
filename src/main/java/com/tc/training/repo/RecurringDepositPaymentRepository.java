package com.tc.training.repo;

import com.tc.training.model.RecurringDepositPayment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface RecurringDepositPaymentRepository extends ReactiveCrudRepository<RecurringDepositPayment, UUID> {

    @Query(value = "select * from recurring_deposit_payment where recurring_deposit_id = :1")
    Flux<RecurringDepositPayment> findByRecurringDepositId(UUID id);
}
