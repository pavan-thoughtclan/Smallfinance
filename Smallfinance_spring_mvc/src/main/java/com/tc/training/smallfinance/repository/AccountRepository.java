package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails,Long> {

    AccountDetails findByUser(User user);

}
