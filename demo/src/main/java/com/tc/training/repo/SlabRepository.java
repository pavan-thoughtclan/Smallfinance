package com.tc.training.repo;

import com.tc.training.model.Slabs;
import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SlabRepository extends ReactiveCrudRepository<Slabs, UUID> {
   // @Query(value = "Select * from Slabs where tenures =1? && typeOfTransaction =2?",nativeQuery = true)
    public Mono<Slabs> findByTenuresAndTypeOfTransaction(Tenures tenures, TypeOfTransaction typeOfTransaction);


}
