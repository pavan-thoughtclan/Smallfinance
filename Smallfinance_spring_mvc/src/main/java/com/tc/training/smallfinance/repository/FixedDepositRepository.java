package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.FixedDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, UUID> {

    @Query(value = "SELECT * FROM fixed_deposit WHERE account_number = ?1",nativeQuery = true)
    List<FixedDeposit> findByAccountNumber(Long accountNumber);
    @Query(value = "select * from fixed_deposit where is_active = true",nativeQuery = true)
    List<FixedDeposit> findAllByIsActive();

    @Query(value = "select * from fixed_deposit where is_active = true and account_number = ?1",nativeQuery = true)
    List<FixedDeposit> findByAccountNumberAndIsActive(Long accNo);

}
