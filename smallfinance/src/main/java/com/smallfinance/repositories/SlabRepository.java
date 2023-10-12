package com.smallfinance.repositories;

import com.smallfinance.entities.Slabs;
import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfSlab;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface SlabRepository extends JpaRepository<Slabs, UUID> {
//   @Query(value = "select * from slabs where tenures =:tenures AND type_of_transaction=:typeOfTransaction",nativeQuery = true)
   Slabs findByTenuresAndTypeOfTransaction(Tenures tenures, TypeOfSlab typeOfTransaction);
}
