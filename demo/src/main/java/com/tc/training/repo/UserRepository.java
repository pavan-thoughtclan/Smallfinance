package com.tc.training.repo;

import com.tc.training.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,UUID> {
}
