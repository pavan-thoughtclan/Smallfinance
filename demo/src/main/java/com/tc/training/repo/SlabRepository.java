package com.tc.training.repo;

import com.tc.training.model.Slabs;
import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfSlab;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SlabRepository extends ReactiveCrudRepository<Slabs, UUID> {
    @Query(value = "Select * from slabs where tenures =:1 and type_of_transaction=:2")
    public Mono<Slabs> findByTenuresAndTypeOfSlab(String tenures, String typeOfSlab);


}
