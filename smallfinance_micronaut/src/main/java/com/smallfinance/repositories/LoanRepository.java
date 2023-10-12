package com.smallfinance.repositories;

import com.smallfinance.entities.Loan;
import com.smallfinance.enums.Status;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.http.annotation.QueryValue;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    @Query(value = "select * from loan where account_number = :accNo",nativeQuery = true)
    List<Loan> findAllByAccountNumber(@QueryValue Long accNo);

//    @Query(value = "select * from loan where account_number = ?1", nativeQuery = true)
//    List<Loan> findAllByAccountNumber(Long accNo);
//    @Query(value = "select * from loan where is_active = true",nativeQuery = true)
//    List<Loan> findByIsActive();

    @Query(value = "select * from loan where account_number = :accNo and is_active = true",nativeQuery = true)
    List<Loan> findAllByAccountNumberAndIsActive(Long accNo);

    @Query(value = "select * from loan where status = 'UNDER_REVIEW'",nativeQuery = true)
    List<Loan> findAllPending();

    @Query(value = "select * from loan where not status = 'UNDER_REVIEW'",nativeQuery = true)
    List<Loan> findAllNotPending();
    @Query(value = "select * from loan where is_active = true and status = true",nativeQuery = true)
    List<Loan> findByIsActiveAndAccepted();

    List<Loan> findByStatus(Status status);
}
