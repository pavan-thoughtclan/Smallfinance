package com.smallfinance.repositories;

import com.smallfinance.entities.Transaction;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.http.annotation.QueryValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
//    @Query("SELECT t.column1, t.column2, t.column3 FROM transactions t WHERE (t.from_account_number = :accNo OR t.to_account_number = :accNo) AND t.timestamp BETWEEN :localDateTime1 AND :localDateTime2")
//    List<Transaction> findAllByUserAndDate(
//            @QueryValue("localDateTime1") LocalDateTime localDateTime1,
//            @QueryValue("localDateTime2") LocalDateTime localDateTime2,
//            @QueryValue("accNo") Long accNo
//    );
    @Query(value = "select * from transactions where from_account_number = ?1 or to_account_number  = ?1",nativeQuery = true)
     List<Transaction> findAllByUser(Long accNo);

    @Query(value = "select * from transactions t where (t.from_account_number = :accNo OR t.to_account_number = :accNo) and t.timestamp between :date1 and :date2",nativeQuery = true)
    List<Transaction> findAllByUserAndDate(LocalDateTime date1, LocalDateTime date2, Long accNo);
}
