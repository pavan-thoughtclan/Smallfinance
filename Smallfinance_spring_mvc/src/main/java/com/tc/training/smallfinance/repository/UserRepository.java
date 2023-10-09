package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    @Query(value = "select * from user where email = ?1",nativeQuery = true)
    User findByEmail(String userName);
    @Query(value = "select * from users e where e.account_number = ?1",nativeQuery = true)
    User findByAccountNumber(String accountNumber);
    @Query(value = "select * from users e where e.first_name = ?1",nativeQuery = true)
    User findByName(String userName);
    @Query(value = "select * from users e where e.firebase_id = ?1",nativeQuery = true)
    User findByFirebaseId(String user_id);

    @Query(value = "SELECT * FROM users WHERE role_name = 'CUSTOMER'", nativeQuery = true)
    List<User> findByRoleNameCustomer();
}
