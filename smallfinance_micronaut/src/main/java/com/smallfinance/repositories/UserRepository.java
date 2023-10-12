package com.smallfinance.repositories;

import com.smallfinance.entities.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "select * from user e where e.email = ?1",nativeQuery = true)
    User findByEmail(String userName);

    @Query(value = "SELECT * FROM \"user\" WHERE role_name = 'CUSTOMER'", nativeQuery = true)
    List<User> findByRoleNameCustomer();
}
