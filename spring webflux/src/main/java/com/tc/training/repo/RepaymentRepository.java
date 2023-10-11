package com.tc.training.repo;

import com.tc.training.model.Repayment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepaymentRepository extends ReactiveCrudRepository<Repayment, UUID> {
}
