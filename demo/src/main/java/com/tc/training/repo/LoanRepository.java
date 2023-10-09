package com.tc.training.repo;

import com.tc.training.model.Loan;
import com.tc.training.utils.Status;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends ReactiveCrudRepository<Loan, UUID> {
    @Query(value = "select * from loan l where l.account_number = :1")
    Flux<Loan> findAllByAccountNumber(Long accNo);

    @Query(value = "select * from loan where is_active = true")
    Flux<Loan> findByIsActive();

    @Query(value = "select * from loan where account_number = :1 and is_active = true")
    Flux<Loan> findAllByAccountNumberAndIsActive(Long accNo);

    @Query(value = "select * from loan where status = 'UNDER_REVIEW'")
    Flux<Loan> findAllPending();

    @Query(value = "select * from loan where not status = 'UNDER_REVIEW'")
    Flux<Loan> findAllNotPending();
    @Query(value = "select * from loan where is_active = true and status = 'APPROVED'")
    Flux<Loan> findByIsActiveAndAccepted();

    Flux<Loan> findByStatus(Status status);
}
