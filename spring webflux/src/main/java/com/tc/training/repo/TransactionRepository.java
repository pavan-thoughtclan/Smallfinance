package com.tc.training.repo;

import com.tc.training.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {
   @Query(value = "select * from transactions t where (t.from_account_number = :accNo OR t.to_account_number = :accNo) and t.timestamp between :localDateTime1 and :localDateTime2") //
   Flux<Transaction> findAllByUserAndDate(
           @Param("localDateTime1") LocalDateTime localDateTime1,
           @Param("localDateTime2") LocalDateTime localDateTime2,
           @Param("accNo") Long accNo
   );
}
