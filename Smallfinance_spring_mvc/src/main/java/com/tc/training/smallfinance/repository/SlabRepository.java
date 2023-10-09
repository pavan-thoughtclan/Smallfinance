package com.tc.training.smallfinance.repository;

import com.tc.training.smallfinance.model.Slabs;
import com.tc.training.smallfinance.utils.Tenures;
import com.tc.training.smallfinance.utils.TypeOfSlab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SlabRepository extends JpaRepository<Slabs, UUID> {
   // @Query(value = "Select * from Slabs where tenures =1? && typeOfTransaction =2?",nativeQuery = true)
    public Slabs findByTenuresAndTypeOfTransaction(Tenures tenures, TypeOfSlab typeOfTransaction);


}
