package com.microFinance1.entities;

import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfSlab;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@MappedEntity("slabs")
@Getter
@Setter
@ToString
@Introspected
public class Slabs {
    @Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;

    private Tenures tenures;

    private String interestRate;

    private TypeOfSlab typeOfTransaction;
}
