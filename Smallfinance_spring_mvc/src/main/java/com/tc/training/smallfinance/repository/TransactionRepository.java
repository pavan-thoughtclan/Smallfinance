package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "select * from transactions where from_account_number = ?1 or to_account_number  = ?1",nativeQuery = true)
    List<Transaction> findAllByUser(Long accNo);
    @Query(value = "select * from transactions t where (t.from_account_number = ?3 OR t.to_account_number = ?3) and t.timestamp between ?1 and ?2",nativeQuery = true)
    List<Transaction> findAllByUserAndDate(LocalDateTime date1, LocalDateTime date2, Long accNo);
}
