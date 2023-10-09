package com.tc.training.repo;

import com.tc.training.model.AccountDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AccountDetailsRepository extends ReactiveCrudRepository<AccountDetails, Long> {
    @Query(value = "select * from account_details where user_id = :1")
    Mono<AccountDetails> findByUserID(UUID userId);
}
