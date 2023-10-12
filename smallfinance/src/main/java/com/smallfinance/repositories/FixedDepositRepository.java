package com.smallfinance.repositories;

import com.smallfinance.entities.FixedDeposit;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, UUID> {
    @Query(value = "SELECT * FROM fixed_deposit WHERE account_number = :accountNumber",nativeQuery = true)
    List<FixedDeposit> findByAccountNumber(Long accountNumber);

    @Query(value = "select * from fixed_deposit where is_active = true",nativeQuery = true)
    List<FixedDeposit> findAllByIsActive();

    @Query(value = "select * from fixed_deposit where is_active = true and account_number = :accNo",nativeQuery = true)
    List<FixedDeposit> findByAccountNumberAndIsActive(Long accNo);

}
