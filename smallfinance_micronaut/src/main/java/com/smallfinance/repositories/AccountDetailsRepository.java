package com.smallfinance.repositories;

import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
//    @Query("SELECT ad FROM AccountDetails ad WHERE ad.user = :user")
@Query(value = "select * from account_details where user_id = :userId",nativeQuery = true)
AccountDetails findByUserId(UUID userId);

   @Override
   List<AccountDetails> findAll();
}
