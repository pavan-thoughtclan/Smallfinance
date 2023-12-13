package com.smallfinance.repositories;

import com.smallfinance.entities.Repayment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, UUID> {
}
