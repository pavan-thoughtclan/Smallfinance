package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RePaymentRepository extends JpaRepository<Repayment, UUID> {
}
