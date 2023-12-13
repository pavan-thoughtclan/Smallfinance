package com.smallfinance.entities;

import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfSlab;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Slabs {
//    @GeneratedValue(GeneratedValue.Type.UUID)
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Tenures tenures;

    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "type_of_transaction")
    @Enumerated(EnumType.STRING)
    private TypeOfSlab typeOfTransaction;
}
